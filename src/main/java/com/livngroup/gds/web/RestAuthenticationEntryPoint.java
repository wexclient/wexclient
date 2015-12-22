package com.livngroup.gds.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Controller;

import com.livngroup.gds.domain.WexEntity;
import com.livngroup.gds.exception.WexRuntimeException;
import com.livngroup.gds.response.ErrorResponse;

//@Controller 
public final class RestAuthenticationEntryPoint /*implements AuthenticationEntryPoint*/ {

	//@Override
	public void commence(HttpServletRequest request, HttpServletResponse response/*,
	        AuthenticationException authException*/) throws IOException, ServletException {
		
//		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
		
//		throw new WexRuntimeException(authException, new ErrorResponse(
//				HttpStatus.UNAUTHORIZED, 
//				HttpStatus.UNAUTHORIZED.toString(),
//				WexEntity.GENERAL,
//				"Unauthorized access", 
//				ErrorResponse.URL_DEFAULT, 
//				null, 
//				null));		
		
	}

}	

