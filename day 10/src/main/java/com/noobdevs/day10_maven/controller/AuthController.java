package com.noobdevs.day10_maven.controller;

import com.noobdevs.day10_maven.dto.AuthRequest;
import com.noobdevs.day10_maven.dto.AuthResponse;
import com.noobdevs.day10_maven.dto.ClientDTO;
//import com.noobdevs.day10_maven.security.JwtUtil;
import com.noobdevs.day10_maven.security.JwtUtil;
import com.noobdevs.day10_maven.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

/**
 * Public entry points: nothing here requires a token (see SecurityConfig's
 * "/auth/**" permitAll rule) since you need to register/log in before you
 * have one.
 *
 * Only client self-registration is public here. Letting anyone hit a public
 * "register as admin" endpoint would be a privilege-escalation bug, so admin
 * accounts are created either by an existing admin (POST /api/admins, which
 * SecurityConfig locks to ROLE_ADMIN) or by the startup seeder — see
 * {@link com.noobdevs.day10_maven.config.AdminSeeder}.
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final ClientService clientService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    public ClientDTO register(@RequestBody ClientDTO clientDTO) {
        return clientService.createClient(clientDTO);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {
        try {
            var authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

            String role = authentication.getAuthorities().stream()
                    .findFirst()
                    .map(GrantedAuthority::getAuthority)
                    .map(a -> a.replace("ROLE_", ""))
                    .orElseThrow();

            String token = jwtUtil.generateToken(request.getEmail(), role);
            return new AuthResponse(token, role);
        } catch (BadCredentialsException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password");
        }
    }
}
