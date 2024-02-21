package fr.univartois.butinfo.s5a01.musicmatcher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import fr.univartois.butinfo.s5a01.musicmatcher.auth.JwtAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class FilterChainConfiguration {
	
	@Autowired
	private JwtAuthenticationFilter jwtAuthFilter;
	
	@Autowired
	private AuthenticationProvider authenticationProvider;

	@Bean
	public SecurityFilterChain getSecurityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(AbstractHttpConfigurer::disable);
		http.sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		http.authenticationProvider(authenticationProvider);
		http.authorizeHttpRequests(req ->
	    req.requestMatchers("/swagger-ui/index.html").permitAll()
		    .requestMatchers("/swagger-ui/**").permitAll()
		    .requestMatchers("/swagger-resources/**").permitAll()
		    .requestMatchers("/swagger-ui.html").permitAll()
		    .requestMatchers("/v2/api-docs/**").permitAll()
		    .requestMatchers("/v3/api-docs/**").permitAll()
		    .requestMatchers("/api/auth/login").permitAll()
		    .requestMatchers("/api/auth/**").permitAll()
	        .anyRequest().authenticated());
		http.addFilterAfter(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
}
