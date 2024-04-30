package com.news_co_api.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.news_co_api.app.modules.viewer.ViewerRepository;

@Configuration
public class SessionConfig {
    @Bean
    UserDetailsService userDetailsService(ViewerRepository userRepo) {
        return (username) -> userRepo
                .findByUsername(username)
                .orElseThrow(() -> {
                    var error = String.format("Username: %s not found.", username);
                    return new UsernameNotFoundException(error);
                });
    }

    @Bean
    AuthenticationManager authenticationManager(UserDetailsService userDetailsService, PasswordEncoder encoder) {
        return authentication -> {
            var username = authentication.getPrincipal().toString();
            var password = authentication.getCredentials().toString();

            try {
                var user = userDetailsService.loadUserByUsername(username);
                var samePassword = encoder.matches(password, user.getPassword());

                if (!samePassword) {
                    throw new BadCredentialsException("Invalid username/password supplied");
                }

                if (!user.isEnabled()) {
                    throw new DisabledException("Viewer account is not active");
                }
         

                return new UsernamePasswordAuthenticationToken(
                        username, null, user.getAuthorities());
            } catch (UsernameNotFoundException e) {
                throw new BadCredentialsException("Invalid username/password supplied");
            }
        };
    }
}