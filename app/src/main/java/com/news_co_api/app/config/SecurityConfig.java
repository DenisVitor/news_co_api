package com.news_co_api.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.news_co_api.app.security.JwtTokenAuthenticationFilter;
import com.news_co_api.app.security.JwtTokenProvider;

@Configuration
public class SecurityConfig {
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http, JwtTokenProvider tokenProvider) throws Exception {
        var tokenFilter = new JwtTokenAuthenticationFilter(tokenProvider);
        return http.httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(
                        a -> a.requestMatchers(HttpMethod.GET, "/news").permitAll()
                                .requestMatchers(HttpMethod.GET, "/").permitAll()
                                .requestMatchers(HttpMethod.GET, "/reviews").permitAll()
                                .requestMatchers(HttpMethod.GET, "/journalists").permitAll()
                                .requestMatchers(HttpMethod.GET, "/viewers").permitAll()
                                .requestMatchers(HttpMethod.GET, "/news/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/reviews/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/journalists/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/viewers/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                                .requestMatchers(HttpMethod.POST, "/auth/register").permitAll()
                                .requestMatchers(HttpMethod.POST, "/reviews").hasAnyRole("ADMIN", "COMMON")
                                .requestMatchers(HttpMethod.PATCH, "/reviews").hasAnyRole("ADMIN", "COMMON")
                                .requestMatchers(HttpMethod.DELETE, "/reviews").hasAnyRole("ADMIN", "COMMON")
                                .requestMatchers(HttpMethod.POST, "/viewers/**").hasAnyRole("ADMIN", "COMMON")
                                .requestMatchers(HttpMethod.PATCH, "/viewers/**").hasAnyRole("ADMIN", "COMMON")
                                .requestMatchers(HttpMethod.DELETE, "/viewers/**").hasAnyRole("ADMIN", "COMMON")
                                .anyRequest().denyAll())
                .addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
