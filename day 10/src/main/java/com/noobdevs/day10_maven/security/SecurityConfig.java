package com.noobdevs.day10_maven.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Wires the whole request pipeline together:
 *
 * incoming request -> JwtAuthFilter (reads token, sets SecurityContext)
 *                  -> Spring Security's own filters
 *                  -> authorizeHttpRequests rules below decide 200 vs 401/403
 *                  -> controller method
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final CustomUserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // login/register must be reachable without a token
                        .requestMatchers("/auth/**").permitAll()
//                        // Spring Boot forwards here internally to render error responses;
//                        // without this, an anonymous request that throws (e.g. bad login)
//                        // gets a 403 from Security's own AuthorizationFilter on the forward,
//                        // masking whatever status the controller actually intended.
//                        .requestMatchers("/error").permitAll()
//                        // endpoint-specific access: only the matching role can call these
//                        .requestMatchers("/api/admins/**").hasRole("ADMIN")
//                        .requestMatchers("/api/clients/**").authenticated()
//                        // shared endpoints: any logged-in user, admin or client
//                        .requestMatchers("/api/users/**").authenticated()
                        .anyRequest().authenticated()
                )
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
