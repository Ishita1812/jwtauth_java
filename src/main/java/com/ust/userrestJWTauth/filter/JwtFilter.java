package com.ust.userrestJWTauth.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ust.userrestJWTauth.service.CustomUserDetailsService;
import com.ust.userrestJWTauth.util.JwtUtil;

@Component
public class JwtFilter extends OncePerRequestFilter {

	@Autowired
	JwtUtil jwtUtil;
	
	@Autowired
	CustomUserDetailsService customUserDetailsService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String authHeader = request.getHeader("Authorization");
		
		//Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ6YWlkIiwiZXhwIjoxNjY5Mzc1Mzk2LCJpYXQiOjE2NjkzNTczOTZ9.uewMROdjAAo9AHPwjrte6LvuG3YXJR1IRusf1GF32nxqb5mbklrKAYcfeMkmrVdp0eG0yGInFTgEs2rKRcJiAg
		
		String token = null;
		String username = null;
		
		if(authHeader != null && authHeader.startsWith("Bearer"))
		{
			token = authHeader.substring(7);
			username = jwtUtil.getUserNameFromToken(token);
		}
		
		if(username != null && SecurityContextHolder.getContext().getAuthentication() == null)
		{
			UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
			
			if(jwtUtil.validateToken(token, userDetails))
			{
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
			
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				
			}
		}
		filterChain.doFilter(request, response);
		
	}

}
