package com.backend.stayEasy.config;

import static com.backend.stayEasy.enums.Permission.ADMIN_CREATE;
import static com.backend.stayEasy.enums.Permission.ADMIN_DELETE;
import static com.backend.stayEasy.enums.Permission.ADMIN_READ;
import static com.backend.stayEasy.enums.Permission.ADMIN_UPDATE;
import static com.backend.stayEasy.enums.Permission.OWNER_READ;
import static com.backend.stayEasy.enums.Role.ADMIN;
import static com.backend.stayEasy.enums.Role.OWNER;
import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.backend.stayEasy.filter.JwtAuthenticationFilter;
import com.backend.stayEasy.sevice.LogoutService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {
	
	@Autowired
	private LogoutService logoutService;

	private static final String[] WHITE_LIST_URL = {"/api/v1/auth/**", "/api/v1/stayeasy/**"};
	private final JwtAuthenticationFilter jwtAuthFilter;
	private final AuthenticationProvider authenticationProvider;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.cors(cors -> cors.configurationSource(corsConfigurationSource()))
		.csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests(
						req -> req.requestMatchers(WHITE_LIST_URL).permitAll()
						.requestMatchers("/api/v1/owner/**").hasAnyRole(OWNER.name())
						.requestMatchers("/api/v1/user/**").hasAnyRole(ADMIN.name())
						.requestMatchers(GET, "/api/v1/user/**").hasAnyAuthority(ADMIN_READ.name(), OWNER_READ.name())
						.requestMatchers(POST, "/api/v1/user/**").hasAnyAuthority(ADMIN_CREATE.name())
						.requestMatchers(PUT, "/api/v1/user/**").hasAnyAuthority(ADMIN_UPDATE.name())
						.requestMatchers(DELETE, "/api/v1/user/**").hasAnyAuthority(ADMIN_DELETE.name())
						.anyRequest().authenticated())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authenticationProvider(authenticationProvider)
				.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
				.logout(logout -> logout.logoutUrl("/api/v1/auth/logout")
						.addLogoutHandler(logoutService)
						.logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext()));

		return http.build();
	}
	
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration corsConfiguration = new CorsConfiguration();

		corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
		corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
		corsConfiguration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
		corsConfiguration.setExposedHeaders(Arrays.asList("Authorization"));
		corsConfiguration.setAllowCredentials(true);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", corsConfiguration);

		return source;
	}
}
