package com.Obee1.JwtRoleBasedApi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import jakarta.annotation.Resource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {

    @Resource(name = "userService")
    private final UserDetailsService userDetailsService;

    private final UnauthorizedEntryPoint unauthorizedEntryPoint;

    private final PasswordEncoder encoder;

    public WebSecurityConfig(UnauthorizedEntryPoint unauthorizedEntryPoint, UserDetailsService userDetailsService, PasswordEncoder encoder) {
        this.unauthorizedEntryPoint = unauthorizedEntryPoint;
        this.userDetailsService = userDetailsService;
        this.encoder = encoder;
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder auth = http.getSharedObject(AuthenticationManagerBuilder.class);
        auth.userDetailsService(userDetailsService).passwordEncoder(encoder.bcryptPasswordEncoder());
        return auth.build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // Define authorization rules
        http
                .authorizeHttpRequests(authz -> authz
                        // Permit access to authentication and registration endpoints
                        .requestMatchers("/users/authenticate", "/users/register").permitAll()
                        // Require authentication for all other requests
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // No session management
                );

        // Adding custom authentication filter but skip it for public endpoints
        http.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(customizer -> customizer
                        .authenticationEntryPoint(unauthorizedEntryPoint) // Use custom entry point for unauthorized requests
                );

        return http.build();
    }



    @Bean
    public JwtAuthenticationFilter authenticationTokenFilterBean() throws Exception {
        return new JwtAuthenticationFilter();
    }
}
