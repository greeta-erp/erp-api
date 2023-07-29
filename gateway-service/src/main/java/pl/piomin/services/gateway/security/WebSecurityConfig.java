package pl.piomin.services.gateway.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.oauth2.client.oidc.web.server.logout.OidcClientInitiatedServerLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.server.ServerOAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.client.web.server.WebSessionServerOAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.HttpStatusServerEntryPoint;
import org.springframework.security.web.server.authentication.logout.ServerLogoutSuccessHandler;
import org.springframework.security.web.server.csrf.CookieServerCsrfTokenRepository;
import org.springframework.security.web.server.csrf.CsrfToken;
import org.springframework.web.server.WebFilter;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Configuration
@EnableWebFluxSecurity
public class WebSecurityConfig {

    private final JwtAuthConverter jwtAuthConverter;

    @Bean
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) {
        return http
                .authorizeExchange(exchange -> exchange
                        .pathMatchers(HttpMethod.GET, "/department", "/department/**").permitAll()
                        .pathMatchers(HttpMethod.GET, "/employee", "/employee/**").permitAll()
                        .pathMatchers(HttpMethod.GET, "/organization", "/organization/**").permitAll()
                        .pathMatchers(HttpMethod.GET, "/actuator/**").permitAll()
                        .pathMatchers(HttpMethod.GET,"/webjars/**").permitAll()
                        .pathMatchers(HttpMethod.GET,"/", "/swagger-ui.html", "/swagger-ui/**").permitAll()
                        .pathMatchers(HttpMethod.GET,"/swagger-resources/**").permitAll()
                        .pathMatchers(HttpMethod.GET,"/v3/api-docs/**").permitAll()
                        .pathMatchers(HttpMethod.GET,"/department/v3/api-docs/**").permitAll()
                        .pathMatchers(HttpMethod.GET,"/employee/v3/api-docs/**").permitAll()
                        .pathMatchers(HttpMethod.GET,"/organization/v3/api-docs/**").permitAll()
                        .pathMatchers("/employee", "/employee/**").hasRole(ERP_MANAGER)
                        /*.pathMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs", "/v3/api-docs/**", "/", "/**",
                                "/v3/api-docs/swagger-config", "/department/v3/api-docs", "/employee/v3/api-docs",
                                "/organization/v3/api-docs",
                                "/webjars", "/webjars/**",
                                "/webjars/swagger-ui", "/webjars/swagger-ui/**").permitAll()*/
                        .anyExchange().authenticated()
                        .and()
                        .oauth2ResourceServer().jwt()
                        .jwtAuthenticationConverter(new ReactiveJwtAuthenticationConverterAdapter(jwtAuthConverter))
                        .and().and().cors().disable().csrf().disable()
                ).build();
    }

    /*@Bean
    public ReactiveJwtDecoder jwtDecoder(@Value("${spring.security.oauth2.resourceserver.jwt.jwk-set-uri}") String jwkSetUri,
                                 @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}") String jwkIssuerUri) {
        NimbusReactiveJwtDecoder jwtDecoder = NimbusReactiveJwtDecoder
                .withJwkSetUri(jwkSetUri)
                .jwsAlgorithm(SignatureAlgorithm.RS256).build();
        jwtDecoder.setClaimSetConverter(claims -> {
            claims.put("iss", jwkIssuerUri);
            return claims;
        });
        return jwtDecoder;
    }*/

    public static final String ERP_MANAGER = "ERP_MANAGER";
    public static final String USER = "USER";
}
