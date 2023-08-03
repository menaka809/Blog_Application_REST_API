package com.example.blogapprestapi.security;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.blogapprestapi.model.User;
import com.example.blogapprestapi.repository.UserRepository;

@Service
public class CustomerUserDetailService implements UserDetailsService{
	
	

	private UserRepository userRepository;
	
	public CustomerUserDetailService(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
		User user=userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
		.orElseThrow(()-> new UsernameNotFoundException("user not found with username or email :"+usernameOrEmail));
		
		Set<GrantedAuthority> authorities= user
				.getRoles()
				.stream()
				.map((role)-> new SimpleGrantedAuthority(role.getRoleName()))
				.collect(Collectors.toSet());
		
		return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),authorities);
	}
	
	

}
