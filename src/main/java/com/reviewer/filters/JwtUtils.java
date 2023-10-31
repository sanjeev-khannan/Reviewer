package com.reviewer.filters;

import java.io.Serializable;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.reviewer.dao.User;
import com.reviewer.services.UserDetailsServiceImpl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

@Component
public class JwtUtils implements Serializable {

	private static final long serialVersionUID = 8141398511312308520L;
	private static final String SECRET_KEY = "CE0AAF3B6A146732F96F8DBF02EC14FCE0411C923664B4CB6691D5650211CCAF";
	private static final long EXPIRATION_TIME = 86400000; // 24 hours in milliseconds

	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	public static String generateToken(String username) {
		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + EXPIRATION_TIME);
		return Jwts.builder().setSubject(username).setIssuedAt(now).setExpiration(expiryDate)
				.signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
	}

	public User validateToken(String token) {
		try {

			String username = getUsernameFromToken(token);
			Date expirationDate = getExpirationDateFromToken(token);
			User user = this.userDetailsService.loadUserByUsername(username);
			if (expirationDate.before(new Date())) {
				throw new SignatureException("Token Expired");
			}
			if (!username.equals(user.getUsername())) {
				throw new SignatureException("Invalid User");
			}
			return user;
		} catch (SignatureException exp) {
			System.out.println("Exception in Token Validation: " + exp.getMessage());
			return null;
		}
	}

	// for retrieveing any information from token we will need the secret key
	private static Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
	}

	// retrieve username from jwt token
	private static String getUsernameFromToken(String token) {
		Claims claims = getAllClaimsFromToken(token);
		return claims.getSubject();
	}

	// retrieve expiration date from jwt token
	private static Date getExpirationDateFromToken(String token) {
		Claims claims = getAllClaimsFromToken(token);
		return claims.getExpiration();
	}
}