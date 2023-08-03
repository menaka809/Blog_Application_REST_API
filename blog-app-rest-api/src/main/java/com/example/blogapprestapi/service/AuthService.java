package com.example.blogapprestapi.service;

import com.example.blogapprestapi.payload.LoginDto;
import com.example.blogapprestapi.payload.RegisterDto;

public interface AuthService {
	String loginUser(LoginDto loginDto);
	String registerUser(RegisterDto registerDto );

}
