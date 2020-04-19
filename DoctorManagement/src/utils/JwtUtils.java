package utils;

import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import model.User;

public class JwtUtils {

	private static final String secretKey = "abcde";
	private static final long expiration =  3600000 * 24 * 7;
	
	
	public JwtUtils() {
		
	}
	
	public static String generateToken(User u) {
		Claims claims = Jwts.claims().setSubject(u.getUserName());
		
		claims.put("id",u.getId());
		claims.put("username",u.getUserName());
		claims.put("name",u.getName());
		claims.put("type",u.getUserType());
		
		return Jwts.builder()
				.setClaims(claims)
				.signWith(SignatureAlgorithm.HS512, secretKey.getBytes())
				.setExpiration(new Date(System.currentTimeMillis() + expiration))
				.compact();
	}
	
	public static User parseToken(String token) {
		
		try {
			Claims body = Jwts.parser().setSigningKey(secretKey.getBytes()).parseClaimsJws(token).getBody();
			
			User u = new User();
			u.setId((String) body.get("id"));
			u.setUserName((String) body.get("username"));
			u.setName((String) body.get("name"));
			u.setUserType((String) body.get("type"));
			
			return u;
		}catch(JwtException  | ClassCastException e) {
			return null;
		}
		
		
	}
}