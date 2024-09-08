package com.backend.tomagram.util;

import org.springframework.stereotype.Component;

@Component
public class JwtUtil {
    public String getJwt(String authHeader) {
        return authHeader.substring(7);
    }
}
