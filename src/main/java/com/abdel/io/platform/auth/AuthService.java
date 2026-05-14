package com.abdel.io.platform.auth;

import com.abdel.io.platform.user.model.PlatformUser;
import com.abdel.io.platform.user.repository.PlatformUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final PlatformUserRepository repo;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;

    public void register(String email, String password) {

        repo.findByEmail(email).ifPresent(u -> {
            throw new RuntimeException("Email already exists");
        });

        PlatformUser user = new PlatformUser();
        user.setId(UUID.randomUUID());
        user.setEmail(email);
        user.setPasswordHash(encoder.encode(password));
        user.setCreatedAt(Instant.now());

        repo.save(user);
    }

    public String login(String email, String password) {

        PlatformUser user = repo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!encoder.matches(password, user.getPasswordHash())) {
            throw new RuntimeException("Invalid credentials");
        }

        return jwtService.generateToken(user.getId());
    }
}