package com.jciterceros.vr_online_backend.domain.security.config;

import com.jciterceros.vr_online_backend.domain.security.filters.CustomBasicAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final CustomBasicAuthenticationFilter customBasicAuthenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.csrf(csrf -> csrf.disable())
                .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Habilitar h2-console
                .authorizeHttpRequests(request -> request
                        .requestMatchers(HttpMethod.POST, "/api/user/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/h2-console/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/h2-console/**").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/h2-console/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/h2-console/**").permitAll()

                        .anyRequest()
                        .authenticated()
                ).addFilterBefore(customBasicAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
