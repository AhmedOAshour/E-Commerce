package com.vodafone.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vodafone.ecommerce.model.record.TokenLoginRequest;
import com.vodafone.ecommerce.model.record.TokenLoginResponse;
import com.vodafone.ecommerce.service.TokenService;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {


    @Autowired private TokenService tokenService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping(
        value="/token", 
        consumes = MediaType.APPLICATION_JSON_VALUE, 
        produces = MediaType.APPLICATION_JSON_VALUE)
    public TokenLoginResponse getAuthenticationToken(@RequestBody TokenLoginRequest loginRequest)
    {
        Authentication auth = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password())
        );
        String token = tokenService.generateToken(auth);
        return new TokenLoginResponse(token);
    }
    // Login
    @GetMapping("/login")
    public String login() {
        return "login";
    }
    // Register
    @GetMapping("/register")
    public String register() {
        return "register";
    }

    // Reset Password
    @GetMapping("/resetPassword")
    public String resetPassword() {
        return "resetPassword";
    }
    // Verification Confirmed
    @GetMapping("/verify")
    public String verifyEmail() {
        return "verify";
    }
}
