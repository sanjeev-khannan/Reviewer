package com.reviewer.filters;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.reviewer.dao.User;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SessionAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUtils jwtutil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		final String uri = request.getRequestURI();
		System.out.println("Got Request - " + uri);

		final String token = request.getHeader("Authorization");

		if (isPermitFree(uri)) {
			filterChain.doFilter(request, response);
		} else if (token == null) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "AUTHENTICATION_TOKEN_MISSING");
		} else if (token.startsWith("Bearer")) {
			String jwt_token = token.substring(7);
			User user = jwtutil.validateToken(jwt_token);

			if (user != null) {
				Authentication authentication = new UsernamePasswordAuthenticationToken(user, null,
						user.getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(authentication);
				filterChain.doFilter(request, response);
			} else {
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "INVALID_TOKEN");
			}
		} else {
			filterChain.doFilter(request, response);
		}

	}

	private boolean isPermitFree(String uri) {

		if (uri.equals("/") 
				|| uri.startsWith("/ui_files") 
				|| uri.startsWith("/images")
				|| uri.equals("/authenticate")
				|| uri.equals("/signup")) {
			return true;
		}
		return false;
	}

}