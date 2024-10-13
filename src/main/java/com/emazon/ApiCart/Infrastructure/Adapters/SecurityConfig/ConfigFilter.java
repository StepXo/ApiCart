package com.emazon.ApiCart.Infrastructure.Adapters.SecurityConfig;


import com.emazon.ApiCart.Infrastructure.Adapters.SecurityConfig.jwtconfiguration.JwtAuthenticationFilter;
import com.emazon.ApiCart.Infrastructure.Utils.InfraConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.emazon.ApiCart.Infrastructure.Utils.InfraConstants.ROLE_ADMIN;
import static com.emazon.ApiCart.Infrastructure.Utils.InfraConstants.ROLE_USER;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class ConfigFilter {
    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthFilter;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(InfraConstants.DELETE).hasAnyRole(ROLE_USER,ROLE_ADMIN)
                        .requestMatchers(InfraConstants.ORDER).hasAnyRole(ROLE_USER,ROLE_ADMIN)
                        .requestMatchers(InfraConstants.TYPE_ORDER).hasAnyRole(ROLE_USER,ROLE_ADMIN)
                        .anyRequest().authenticated())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
