package com.abdel.io.platform.auth;

import com.abdel.io.platform.dto.auth.AuthResponse;
import com.abdel.io.platform.dto.auth.LoginRequest;
import com.abdel.io.platform.dto.auth.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public void register(@RequestBody RegisterRequest req) {
        authService.register(req.email(), req.password());
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest req) {

        String token = authService.login(req.email(), req.password());

        return new AuthResponse(token);
    }
}