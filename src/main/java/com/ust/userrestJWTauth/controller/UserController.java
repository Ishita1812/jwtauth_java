package com.ust.userrestJWTauth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ust.userrestJWTauth.model.AuthRequest;
import com.ust.userrestJWTauth.service.CustomUserDetailsService;
import com.ust.userrestJWTauth.util.JwtUtil;

@RestController
@CrossOrigin("*")
public class UserController {
	
	@Autowired
	JwtUtil jwtUtil;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	CustomUserDetailsService customUserDetailsService;
	
	@CrossOrigin(origins = "*" )
	@GetMapping("/home")
	public String welcomeUser()
	{
		return "Welcome to Spring JWT";
	}
	
	@PostMapping("/authenticate")
	public String generateToken(@RequestBody AuthRequest authRequest ) throws Exception
	{
		try 
		{
			authenticationManager
			.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(),authRequest.getPassword()));
		} 
		catch (Exception e) 
		{
			throw new Exception("Invalid username or password");
		}
		
		UserDetails userDetails = customUserDetailsService.loadUserByUsername(authRequest.getUsername());
		
		return jwtUtil.generateToken(userDetails);
	}

}
