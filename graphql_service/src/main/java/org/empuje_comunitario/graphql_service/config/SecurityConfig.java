package org.empuje_comunitario.graphql_service.config;

import java.util.Arrays;
import java.util.List;

import org.empuje_comunitario.graphql_service.service.implementations.UserSecurityService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
	            .cors(AbstractHttpConfigurer::disable)
	            .authorizeHttpRequests(auth -> auth
	                    .requestMatchers("/graphiql", "/vendor/**", "/assets/**").permitAll()
	                    .requestMatchers("/graphql").authenticated()
	                    .anyRequest().denyAll()
	            )
	            .httpBasic(Customizer.withDefaults()) // misma autenticación que en gRPC
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
        CorsConfiguration cc = new CorsConfiguration();
        cc.setAllowedOrigins(Arrays.asList(allowedOrigin));
        cc.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH","OPTIONS"));
        cc.setAllowedHeaders(List.of("Authorization", "Content-Type", "email", "password", "roles"));
        cc.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", cc);
        return source;
    }
}
