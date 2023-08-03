package com.example.blogapprestapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.blogapprestapi.payload.JwtAuthResponseDto;
import com.example.blogapprestapi.payload.LoginDto;
import com.example.blogapprestapi.payload.RegisterDto;
import com.example.blogapprestapi.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	private AuthService authService;
	
	
	public AuthController(AuthService authService) {
		super();
		this.authService = authService;
	}
	//login
	@Operation(summary = "Login REST API",description = "Login REST API is used to log user")
	@ApiResponse(responseCode = "200",description = "Http status 200 OK")
	@PostMapping(value = {"/signin","/login"})
	ResponseEntity<JwtAuthResponseDto> userLogin(@RequestBody LoginDto loginDto) {
		String token= authService.loginUser(loginDto);
		JwtAuthResponseDto jwtAuthResponseDto = new JwtAuthResponseDto();
		jwtAuthResponseDto.setAccessToken(token);
			return new ResponseEntity<JwtAuthResponseDto>(jwtAuthResponseDto,HttpStatus.OK);	
	}
	//register
	@Operation(summary = "Register REST API",description = "Registern REST API is used save User information in to the database")
	@ApiResponse(responseCode = "200",description = "Http status 200 OK")
	@PostMapping(value = {"/signup","/register"})
	ResponseEntity<String> userRegister(@RequestBody RegisterDto registerDto) {
		String registerResponse= authService.registerUser(registerDto);
		return new ResponseEntity<String>(registerResponse,HttpStatus.CREATED);	
	}

}
