package com.example.blogapprestapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.blogapprestapi.security.JwtAuthenticationEntryPoint;
import com.example.blogapprestapi.security.JwtAuthenticationFilter;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
@SecurityScheme(
	name="Bear Authentication",
	type=SecuritySchemeType.HTTP,
	bearerFormat = "JWT",
	scheme = "bearer"

)
public class SecurityConfig {
	
	private JwtAuthenticationEntryPoint authenticationEntryPoint;
	
	
	private UserDetailsService userDetailsService;
	private JwtAuthenticationFilter authenticationFilter;
	
	public SecurityConfig(UserDetailsService userDetailsService,JwtAuthenticationEntryPoint authenticationEntryPoint,JwtAuthenticationFilter authenticationFilter) {
		super();
		this.userDetailsService = userDetailsService;
		this.authenticationEntryPoint=authenticationEntryPoint;
		this.authenticationFilter=authenticationFilter;
	}
	
	@Bean
	public static PasswordEncoder passwordEncoder() {
		
		return new BCryptPasswordEncoder();
		
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
		
	}

	
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http ) throws Exception {
		 http.csrf(csrf->csrf.disable())
		 .authorizeHttpRequests(authorize->
//		 authorize.anyRequest().authenticated()
		 authorize.requestMatchers(HttpMethod.GET, "/api/**").permitAll()
		 .requestMatchers("/api/auth/**").permitAll()
		 .requestMatchers("/swagger-ui/**").permitAll()
		 .requestMatchers("/v3/api-docs/**").permitAll()
		 .anyRequest().authenticated()).exceptionHandling(exception->exception
				 .authenticationEntryPoint(authenticationEntryPoint)
				 
				 ).sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		 
		 
		 http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
//	
//	@Bean
//	public UserDetailsService userDetailsService() {
//		UserDetails ramesh=User.builder()
//				.username("ramesh")
//				.password(passwordEncoder().encode("12345"))
//				.roles("USER")
//				.build();
//		UserDetails malith=User.builder()
//				.username("malith")
//				.password(passwordEncoder().encode("123"))
//				.roles("ADMIN")
//				.build();
//		
//		return new InMemoryUserDetailsManager(ramesh,malith);
//	}
//	
//	

}
