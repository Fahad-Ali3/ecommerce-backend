package com.ecommerce.backend.config;

import com.ecommerce.backend.security.CustomUserDetail;
import com.ecommerce.backend.security.JwtAuthFilter;
import com.ecommerce.backend.security.JwtEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebSecurity
@EnableWebMvc
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {

    @Autowired
    private CustomUserDetail customUserDetail;

    @Autowired
    private JwtEntryPoint point;

    @Autowired
    private JwtAuthFilter filter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.disable())// Disable CSRF
                .authorizeHttpRequests(req->req.requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/swagger-ui/**").permitAll()  // Allow access to Swagger UI
                        .requestMatchers("/v3/api-docs/**").permitAll()  // Allow access to API docs
                        .requestMatchers("/swagger-resources/**").permitAll()  // Allow access to Swagger resources
                        .requestMatchers("/webjars/**").permitAll()
                        .anyRequest().authenticated())
                .exceptionHandling(ex -> ex.authenticationEntryPoint(point)) // Handle unauthorized access
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Stateless session
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class); // Add JWT filter

        return httpSecurity.build();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuth = new DaoAuthenticationProvider();
        daoAuth.setUserDetailsService(customUserDetail);
        daoAuth.setPasswordEncoder(passwordEncoder());
        return daoAuth;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}