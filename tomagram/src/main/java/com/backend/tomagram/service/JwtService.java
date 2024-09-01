package com.backend.tomagram.service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private static final String SECRET_KEY = generateKey();

    private static String generateKey(){
        String key = Encoders.BASE64.encode(Jwts.SIG.HS256.key().build().getEncoded());
        System.out.println("***************************************************************************************");
        System.out.println(key);
        System.out.println(Arrays.toString(Decoders.BASE64.decode(key)));
        System.out.println("***************************************************************************************");
        return key;
    }
    public String extractUsername(String jwt){
        return extractClaim(jwt, Claims::getSubject); // Subject should be the email or username
    }

    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(),userDetails);
    }

    public String generateToken(
            Map<String, Object> extraClaims, //pass authorities for example
            UserDetails userDetails)
    {
        return Jwts
                .builder()
                .claims(extraClaims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(getSignInKey(), Jwts.SIG.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }


    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        try {
            return Jwts
                    .parser() // Initialize the JWT parser
                    .verifyWith(getSignInKey()) // Set the secret key for signature validation
                    .build() // Build the JwtParser instance after setting the key
                    .parseSignedClaims(token) // Parse and validate the JWT, returning the parsed JWT object
                    .getPayload(); // Retrieve the claims (payload) from the parsed JWT
        } catch (SecurityException e) {
            // Handle issues with signature validation or key problems
            System.err.println("Invalid JWT signature: " + e.getMessage());
            throw e; // Rethrow the exception or handle it appropriately
        } catch (JwtException e) {
            // Handle general JWT parsing errors (like malformed tokens, expired tokens, etc.)
            System.err.println("Failed to parse JWT: " + e.getMessage());
            throw e; // Rethrow the exception or handle it appropriately
        } catch (Exception e) {
            // Handle any other unexpected errors
            System.err.println("Unexpected error while parsing JWT: " + e.getMessage());
            throw e; // Rethrow the exception or handle it appropriately
        }
    }

    private SecretKey getSignInKey() {
        byte [] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }


}