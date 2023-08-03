package com.example.blogapprestapi.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.blogapprestapi.exception.BlogApiException;
import com.example.blogapprestapi.model.Role;
import com.example.blogapprestapi.model.User;
import com.example.blogapprestapi.payload.LoginDto;
import com.example.blogapprestapi.payload.RegisterDto;
import com.example.blogapprestapi.repository.RoleRepository;
import com.example.blogapprestapi.repository.UserRepository;
import com.example.blogapprestapi.security.JwtTokenProvider;

@Service
public class AuthServiceIml implements AuthService {
	private UserRepository userRepository;
	private RoleRepository roleRepository;
	private PasswordEncoder passwordEncoder;
	private AuthenticationManager authenticationManager;
	private JwtTokenProvider jwtTokenProvider;

	public AuthServiceIml(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder,
			AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
		super();
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
		this.authenticationManager = authenticationManager;
		this.jwtTokenProvider = jwtTokenProvider;
	}

	@Override
	public String loginUser(LoginDto loginDto) {

		// get authenticate object
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginDto.getUserNamrOrEmail(), loginDto.getPassword()));

		// pass to the security context
		SecurityContextHolder.getContext().setAuthentication(authentication);

		String token = jwtTokenProvider.generateToen(authentication);

		return token;
	}

	@Override
	public String registerUser(RegisterDto registerDto) {

		// check username is already exist

		if (userRepository.existsByEmail(registerDto.getEmail())) {
			throw new BlogApiException(HttpStatus.BAD_REQUEST, "Enterd email is already exisit");
		}
		if (userRepository.existsByUsername(registerDto.getUsername())) {
			throw new BlogApiException(HttpStatus.BAD_REQUEST, "Enterd username is already exisit");
		}
		User user = new User();
		// user email already exist
		user.setName(registerDto.getName());
		user.setUsername(registerDto.getUsername());
		user.setEmail(registerDto.getEmail());
		user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

		Set<Role> roles = new HashSet<Role>();
		Role userRole = roleRepository.findByRoleName("ROLE_USER").get();
		roles.add(userRole);
		user.setRoles(roles);

		userRepository.save(user);

		return "User Sucessfully registerd!";

	}

}
