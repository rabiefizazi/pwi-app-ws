package com.elrancho.pwi.security;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import io.jsonwebtoken.Jwts;

public class AuthorizationFilter extends BasicAuthenticationFilter {

	public AuthorizationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws IOException, ServletException {

		String header = req.getHeader(SecurityConstants.HEADER_STRING);
		
		
			
		if (header == null || !header.startsWith(SecurityConstants.TOKEN_PREFIX)) {
			
			//if the url requests to download the inventory data, then the token will be passed as request parameter, otherwise will be in the header
			String path = req.getRequestURI();//.substring(request.getContextPath().length());		
			if(path.endsWith("/inventory.csv") && req.getParameter(SecurityConstants.TOKEN_REQUEST_PARAM).equals("")) {
				chain.doFilter(req, res);
				return;
			}
			
		}

		UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		chain.doFilter(req, res);
	}

	public UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {

		String token;

		String path = request.getRequestURI();//.substring(request.getContextPath().length());
		
		//if the url requests to download the inventory data, then the token will be passed as request parameter, otherwise will be in the header
		if(path.endsWith("/inventory.csv"))
			token = SecurityConstants.TOKEN_PREFIX+request.getParameter(SecurityConstants.TOKEN_REQUEST_PARAM);
		else 
			token = request.getHeader(SecurityConstants.HEADER_STRING);
		
		if (token != null) {

			token = token.replace(SecurityConstants.TOKEN_PREFIX, "");

			String user = Jwts.parser().setSigningKey(SecurityConstants.getTokenSecret()).parseClaimsJws(token).getBody()
					.getSubject();

			if (user != null)
				return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());

		}

		return null;
	}
}
