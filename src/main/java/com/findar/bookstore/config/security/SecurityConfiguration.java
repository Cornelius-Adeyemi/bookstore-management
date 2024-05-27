package com.findar.bookstore.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.CrossOrigin;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@CrossOrigin("*")
@EnableMethodSecurity
public class SecurityConfiguration {

    private final AuthenticationFilter authenticationFilter;
    private final AuthenticationProvider authenticationProvider;

    public static final String[] WHITE_LIST = new String[]{
            "/v3/api-docs.yaml/",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html","/actuator/**",
            "/api/v1/auth/process/**", "/api/v1/admin/process/download-csv-sample"
    };

    @Bean
    public SecurityFilterChain securityFilter(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> request.requestMatchers(WHITE_LIST).permitAll()
                        .requestMatchers("/api/v1/admin/process/**").hasRole("ADMIN")
                        .anyRequest().authenticated())
                .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();

    }
}
