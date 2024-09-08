package com.backend.tomagram.service;


import com.backend.tomagram.dto.AuthenticationRequest;
import com.backend.tomagram.dto.AuthenticationResponse;
import com.backend.tomagram.dto.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    public ResponseEntity<?> register(RegisterRequest request) {
        try {
            userService.createUser(request.getName(), request.getUsername(),request.getEmail(),
                    request.getPassword(),request.getBirthdate());
        } catch (DuplicateKeyException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }

        return ResponseEntity.ok("User Created successfully");
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        try{
            // Authenticate user by its credentials via authenticationManager
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );
            var user = userService.findUser(request.getUsername());
            var jwt = jwtService.generateToken(user);
            AuthenticationResponse response = AuthenticationResponse.builder()
                    .token(jwt)
                    .build();

            return ResponseEntity.ok(response).getBody();
        } catch (UsernameNotFoundException ue){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new AuthenticationResponse("User not found: " + ue.getMessage())).getBody();
        } catch (BadCredentialsException e) {
            // Handle invalid credentials
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new AuthenticationResponse("Invalid username or password")).getBody();
        } catch (Exception e) {
            // Handle all other exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new AuthenticationResponse("An unexpected error occurred: " + e.getMessage())).getBody();
        }

    }
}
