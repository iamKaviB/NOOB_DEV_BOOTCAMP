# Spring Security + JWT — step-by-step

What was added, in the order you asked to learn it, and where to find each
piece in this codebase.

## Hour 1 — Spring Security fundamentals

**Dependency**: `spring-boot-starter-security` in `pom.xml`. The moment this
is on the classpath, Spring Boot auto-configures a default security filter
chain — every endpoint suddenly requires a login, and hitting any of them
without credentials returns `401`/`403` and a generated password is printed
in the startup logs. That's "what breaks and why": Spring Security's
autoconfiguration is all-or-nothing until you replace it with your own
`SecurityFilterChain` bean, which is exactly what `SecurityConfig` does.

**`SecurityFilterChain` — how requests are intercepted**
(`security/SecurityConfig.java`)

Every HTTP request passes through a chain of servlet filters before it
reaches your controller. Spring Security inserts its own filters into that
chain; `securityFilterChain(HttpSecurity http)` is where you configure which
filters run and what rules they enforce:

```java
http
    .csrf(csrf -> csrf.disable())                 // stateless API, no browser forms/cookies to protect
    .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
    .authorizeHttpRequests(auth -> auth ... )      // permitAll vs authenticated, see below
    .authenticationProvider(authenticationProvider())
    .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
```

Request flow: `JwtAuthFilter` runs first (reads the token, if any) → Spring
Security's built-in filters → `authorizeHttpRequests` rules decide
200 vs 401/403 → your controller method runs only if authorized.

**`permitAll` vs `authenticated`**

```java
.requestMatchers("/auth/**").permitAll()          // login/register: no token yet, must stay open
.requestMatchers("/error").permitAll()             // see the gotcha below
.requestMatchers("/api/admins/**").hasRole("ADMIN")
.requestMatchers("/api/clients/**").hasRole("CLIENT")
.requestMatchers("/api/users/**").authenticated()  // any logged-in user, either role
.anyRequest().authenticated()                      // deny by default
```

Rules are matched top to bottom, first match wins — that's why the specific
`/api/admins/**` / `/api/clients/**` rules come before the catch-all
`/api/users/**authenticated()`.

**Gotcha we hit while testing this locally**: Spring Boot forwards internally
to `/error` to render error responses. Without `permitAll()` on `/error`,
an anonymous request that fails login gets a `403` from Security's own
filter intercepting *that internal forward* — masking the `401` your
controller actually intended to send. Confirmed by running:

```
POST /auth/login with wrong credentials  ->  401  (after adding the /error permitAll rule)
```

## Hour 2 — JWT from scratch

**Structure**: `header.payload.signature`, all three base64url-encoded and
dot-joined. `security/JwtUtil.java` builds and reads this by hand using the
`jjwt` library (added to `pom.xml` as `jjwt-api` / `jjwt-impl` / `jjwt-jackson`):

- **header** — `{"alg":"HS256"}`, added automatically by `Jwts.builder()`.
- **payload** — the claims: `sub` (user's email), a custom `role` claim
  (`ADMIN` or `CLIENT`), `iat` (issued-at), `exp` (expiry).
- **signature** — `HMAC-SHA256(header + "." + payload, secretKey)`. Anyone
  can *read* a JWT's payload (it's just base64, not encrypted) but only
  someone holding `jwt.secret` (`application.properties`) can produce a
  signature that validates — so tampering with the payload (e.g. changing
  `role` from `CLIENT` to `ADMIN`) invalidates the signature.

```java
public String generateToken(String email, String role) {
    return Jwts.builder()
            .subject(email)
            .claim("role", role)
            .issuedAt(now)
            .expiration(expiry)
            .signWith(key)
            .compact();
}
```

`validateToken` re-parses the token with the same key; if the signature
doesn't match or `exp` is in the past, it returns `false` rather than
throwing past the caller.

**`JwtAuthFilter extends OncePerRequestFilter`**
(`security/JwtAuthFilter.java`)

Runs once per request (hence the name — regular servlet filters can run
more than once per request during internal forwards, which would waste
work re-validating the same token). It:

1. Reads the `Authorization: Bearer <token>` header.
2. If present and `jwtUtil.validateToken(token)` passes, loads the
   corresponding `UserDetails` and drops a fully-populated
   `UsernamePasswordAuthenticationToken` into `SecurityContextHolder`.
3. Always calls `filterChain.doFilter(...)` — if there's no token or it's
   invalid, the context is simply left empty, and `authorizeHttpRequests`
   rejects the request downstream as anonymous.

This filter is registered with `.addFilterBefore(jwtAuthFilter,
UsernamePasswordAuthenticationFilter.class)` so it runs before Spring
Security's own authentication filter.

## Hour 3 — wiring it to real data

**`UserDetailsService` loads the user from DB**
(`security/CustomUserDetailsService.java`)

```java
User user = userRepository.findByEmail(email)...;
String role = user instanceof Admin ? "ROLE_ADMIN" : "ROLE_CLIENT";
return org.springframework.security.core.userdetails.User
        .withUsername(user.getEmail())
        .password(user.getPassword())      // the BCrypt hash, not plaintext
        .authorities(List.of(new SimpleGrantedAuthority(role)))
        .build();
```

This is where the [[JPA single-table inheritance|INHERITANCE.md]] pays off
directly: one `findByEmail` query against `users`, and the `user_type`
discriminator (surfaced via `instanceof Admin`) becomes the Spring Security
role — `ROLE_ADMIN` or `ROLE_CLIENT`. That's also exactly what makes
`hasRole("ADMIN")` / `hasRole("CLIENT")` in `SecurityConfig` work per
endpoint.

**`BCryptPasswordEncoder` for passwords**

```java
@Bean
public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
}
```

`AdminServiceImpl.createAdmin` and `ClientServiceImpl.createClient` now call
`passwordEncoder.encode(...)` before saving — the raw password never touches
the database. Login re-verifies via `DaoAuthenticationProvider`, which
calls `passwordEncoder.matches(rawPassword, storedHash)` internally; you
never write that comparison by hand.

**`/auth/register` and `/auth/login`** (`controller/AuthController.java`)

- `POST /auth/register` — public, creates a **CLIENT** account only. A
  public "register as admin" endpoint would let anyone hand themselves the
  ADMIN role, so it deliberately doesn't exist.
- `POST /auth/login` — public, authenticates via `AuthenticationManager`,
  then returns `{ "token": "...", "role": "ADMIN"|"CLIENT" }`.

**Bootstrapping the first admin** — since `POST /api/admins` is itself
locked to `hasRole("ADMIN")`, there's a chicken-and-egg problem for the very
first admin account. `config/AdminSeeder.java` (an `ApplicationRunner`)
creates one automatically on startup if none exists yet, using
`app.admin.*` from `application.properties`. Every admin after that is
created by an already-authenticated admin via `POST /api/admins`.

## Testing it — verified live against a local instance

```
curl -X POST /auth/login -d '{"email":"admin@daymaven.com","password":"Admin@123"}'
# -> 200 { "token": "...", "role": "ADMIN" }

curl -X POST /auth/register -d '{"name":"...","email":"ravi@client.com","password":"...","company":"..."}'
# -> 200, self-service client signup, no token required

curl /api/admins -H "Authorization: Bearer <clientToken>"
# -> 403, CLIENT role rejected by hasRole("ADMIN")

curl /api/users -H "Authorization: Bearer <eitherToken>"
# -> 200, returns both ADMIN and CLIENT rows (authenticated() accepts any valid role)
```

The Postman collection (`postman/day10_maven.postman_collection.json`) has
an **Auth** folder with `Register client` / `Login as admin` / `Login as
client` — the two login requests have test scripts that save the returned
JWT into `{{adminToken}}` / `{{clientToken}}`, which every other request in
the collection sends as its `Authorization: Bearer` header.

## Config reference

`application.properties`:

```properties
jwt.secret=<base64, HMAC-SHA256 key>
jwt.expiration-ms=3600000

app.admin.name=Super Admin
app.admin.email=admin@daymaven.com
app.admin.password=Admin@123
app.admin.department=Platform
```

Rotate `jwt.secret` for a real deployment (this repo's value is a
locally-generated placeholder, fine for learning but not for production),
and change `app.admin.password` before deploying anywhere shared.
