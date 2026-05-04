package com.coditas.learningmanagement.util;

import com.coditas.learningmanagement.constants.ExceptionConstants;
import com.coditas.learningmanagement.dto.response.LoginResponseTokens;
import com.coditas.learningmanagement.exception.AuthenticationException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;

@Component
public class JwtUtil {

    private final SecretKey key;
    private final long refreshExpiration;
    private final long accessExpiration;

    public JwtUtil(@Value("${jwt.accessExpiration}")long accessExpiration, @Value("${jwt.refreshExpiration}")long refreshExpiration, @Value("${jwt.secret}")String secret){
        key= Keys.hmacShaKeyFor(secret.getBytes());
        this.accessExpiration=accessExpiration;
        this.refreshExpiration=refreshExpiration;

    }

    public LoginResponseTokens generateTokens(UserDetails userDetails) {
        if(userDetails==null){
            throw new AuthenticationException(ExceptionConstants.USER_NOT_FOUND);
        }
        List<String> roles=userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority).toList();
        String accessToken=generateTokenInternal(userDetails.getUsername(),roles,accessExpiration,"access");
        String refreshToken=generateTokenInternal(userDetails.getUsername(),roles,refreshExpiration,"refresh");
        return  new LoginResponseTokens(accessToken,refreshToken);
    }

    public String generateTokenInternal(String username, List<String> roles,long expirationTime,String type){
        return Jwts.builder()
                .setSubject(username)
                .setIssuer("TaskManagementApp")
                .setIssuedAt(new Date())
                .claim("roles",roles)
                .claim("type",type)
                .setExpiration(new Date(System.currentTimeMillis()+expirationTime))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

    }

    public String extractTypeFromToken(String token){
       return extractClaims(token).get("type",String.class);
    }

    public Claims extractClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractUsername(String token){
        return extractClaims(token).getSubject();
    }

    public boolean validateToken(UserDetails userDetails,String username,String token){
        return username.equals(userDetails.getUsername()) && !isExpired(token);
    }

    public boolean isExpired(String token){
        return extractClaims(token).getExpiration().before(new Date());
    }
}
