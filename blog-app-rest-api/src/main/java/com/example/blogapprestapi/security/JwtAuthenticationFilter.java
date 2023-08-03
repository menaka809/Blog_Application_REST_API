package com.example.blogapprestapi.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{

	private JwtTokenProvider jwtTokenProvider;
	
	private UserDetailsService userDetailsService;
	
	
	
	
	public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider, UserDetailsService userDetailsService) {
		super();
		this.jwtTokenProvider = jwtTokenProvider;
		this.userDetailsService = userDetailsService;
		
	}



	@Override
	protected void doFilterInternal(HttpServletRequest request, 
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		//get jwt token from http request
		String token = getTokenFromRequest(request);
		
		//validate token
		if(StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)) {
			//get username from the  token
			String username= jwtTokenProvider.getUsrName(token);
			
			//load the user from database 
			UserDetails userDetails= userDetailsService.loadUserByUsername(username);
			
			//create user name password authentiaction object pass the user object
			UsernamePasswordAuthenticationToken authenticationToken= new UsernamePasswordAuthenticationToken(
					userDetails,
					null,
					userDetails.getAuthorities()
					
					);
			authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			
			SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		}
		
		filterChain.doFilter(request, response);
		
		
	}
	
	private String getTokenFromRequest(HttpServletRequest request) {
		
		String bearerToken =request.getHeader("Authorization");
		
		//extract only token value
		
		if(StringUtils.hasText(bearerToken)&& bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7,bearerToken.length());
		}
		return null;
	}

}
