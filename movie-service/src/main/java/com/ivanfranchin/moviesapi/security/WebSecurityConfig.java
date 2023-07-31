package com.ivanfranchin.moviesapi.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final JwtAuthConverter jwtAuthConverter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        final CorsConfiguration cors_config = new CorsConfiguration();
        cors_config.setAllowCredentials(true);
        //cors_config.applyPermitDefaultValues();
        cors_config.setAllowedOriginPatterns(List.of("*"));
        cors_config.setAllowedHeaders(List.of("*"));
        cors_config.setAllowedMethods(List.of("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        return http
                .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                        .requestMatchers(HttpMethod.GET, "/movie", "/movie/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/actuator/**").permitAll()
                        .requestMatchers("/movie/*/comments").hasAnyRole(ERP_MANAGER, ERP_USER)
                        .requestMatchers("/movie", "/movie/**").hasRole(ERP_MANAGER)
                        .requestMatchers("/movie/userextras/me").hasAnyRole(ERP_MANAGER, ERP_USER)
                        .requestMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs", "/v3/api-docs/**").permitAll()
                        .anyRequest().authenticated())
                .oauth2ResourceServer(oauth2ResourceServer -> oauth2ResourceServer.jwt(
                        jwt -> jwt.jwtAuthenticationConverter(jwtAuthConverter)))
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                //.cors(Customizer.withDefaults())
                .cors().configurationSource(source -> cors_config)
                .and()
                .csrf(AbstractHttpConfigurer::disable)
                .build();
    }

    public static final String ERP_MANAGER = "ERP_MANAGER";
    public static final String ERP_USER = "ERP_USER";
}