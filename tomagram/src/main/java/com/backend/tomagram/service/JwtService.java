package com.backend.tomagram.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.IOException;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.PrivateKey;

@Service
public class JwtService {

    private static final String SECRET_KEY = "OTUxMGJhZDFlNWYwNWFkYTJiNmE5NjVjMWQwNjUzMTYxNTMxMTQwZTYxMjhmNTBmOTY3M2QyNDk5OGJlNzc1Ng==";
    public String extractUsername(String jwt){
        return null;
    }
    private Claims extractAllClaims(String token) {
        return Jwts
                .parser() // Parser supposed to Parse JWT
                .decryptWith(getSignInKey())  // set Secret key (public key) for signature validation
                .build() // return JwtParser object after secret key configured
                .parseSignedClaims(token) // the actual parsing and validation of the JWT happens
                .getPayload(); // get payload
    }

    private SecretKey getSignInKey() {
        byte [] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }


}