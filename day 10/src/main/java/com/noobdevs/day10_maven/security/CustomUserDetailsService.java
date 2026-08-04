package com.noobdevs.day10_maven.security;

import com.noobdevs.day10_maven.model.Admin;
import com.noobdevs.day10_maven.model.User;
import com.noobdevs.day10_maven.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Loads a Spring Security {@link UserDetails} straight from the "users" table.
 * The discriminator column tells us the role: an ADMIN row becomes ROLE_ADMIN,
 * a CLIENT row becomes ROLE_CLIENT.
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("No user with email: " + email));

        String role = user instanceof Admin ? "ROLE_ADMIN" : "ROLE_CLIENT";

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .authorities(List.of(new SimpleGrantedAuthority(role)))
                .build();
    }
}
