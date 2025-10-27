package org.empuje_comunitario.rest_service.config;

import java.util.Arrays;
import java.util.List;

import org.empuje_comunitario.rest_service.service.Implementations.UserSecurityService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	private final UserSecurityService userSecurityService;
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    return http
	            .csrf(AbstractHttpConfigurer::disable)
	            .cors(Customizer.withDefaults())
	            .authorizeHttpRequests(auth -> auth
	                    //Endpoints públicos GET:
	                    .requestMatchers(HttpMethod.GET,
	                            "/api/reportes/**",
	                            "/api/v1/filtros-eventos/**").permitAll()
	                    //Swagger y documentación pública:
	                    .requestMatchers(
	                            "/swagger-ui.html",
	                            "/swagger-ui/**",
	                            "/v3/api-docs/**").permitAll()
	                    //Cualquier otro endpoint requiere autenticación:
	                    .anyRequest().authenticated()
	            )
	            .httpBasic(Customizer.withDefaults())
	            .build();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationProvider authenticationProvider() {
	    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
	    authProvider.setUserDetailsService(userSecurityService);
	    authProvider.setPasswordEncoder(passwordEncoder());
	    return authProvider;
	}
	
	@Bean
	CorsConfigurationSource corsConfigurationSource(@Value("${cors.allowed.origin}") String allowedOrigin) {
	    CorsConfiguration configuration = new CorsConfiguration();
	    configuration.setAllowedOrigins(List.of(allowedOrigin));
	    configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
	    configuration.setAllowedHeaders(List.of("Authorization", "Content-Type", "Accept", "email", "password", "roles"));
	    configuration.setExposedHeaders(List.of("Content-Disposition"));
	    configuration.setAllowCredentials(true);
	    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	    source.registerCorsConfiguration("/**", configuration);
	    return source;
	}

}
