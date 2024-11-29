package com.furkan.spring_security_jwt.service;



import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;

import java.util.*;


@Service
public class JwtService {

    @Value("${jwt.key}")
    String SECRET_KEY;

    public String generateToken(String userName) {
        Map<String, Object> claims = new HashMap<>();
        return createToken( claims , userName);
    }

    public boolean validateToken(String token, UserDetails userDetails){
        String username = extractUser(token);
        Date expirationDate = extractExpiration(token);
        return userDetails.getUsername().equals(username) && !expirationDate.before(new Date());

    }

    public Date extractExpiration(String token){
        // Claims payload içersindeki key value değerleri
        Claims claims = Jwts.parser()
                .setSigningKey(getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload(); //getBody(); deprecated
        return claims.getExpiration();

    }

    public String extractUser(String token){
        // Claims payload içersindeki key value değerleri
        Claims claims = Jwts.parser()
                .setSigningKey(getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload(); //getBody(); deprecated
        return claims.getSubject();

    }

    private String createToken(Map<String,Object> claims, String username){
        return Jwts.builder()
                .claims(claims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis())) // tokenın üretildiği zaman
                .expiration(new Date(System.currentTimeMillis() + 1000 * 30 * 100)) //tokenın  geçersiz olacağı zaman
                .signWith(getSignKey())
                .compact();
    }

    private Key getSignKey(){
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
