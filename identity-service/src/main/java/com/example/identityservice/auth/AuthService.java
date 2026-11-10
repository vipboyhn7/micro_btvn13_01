package com.example.identityservice.auth;

import com.example.identityservice.user.Role;
import com.example.identityservice.user.User;
import com.example.identityservice.user.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public RegisterResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.username())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username is already registered");
        }

        User user = new User(
                request.username(),
                passwordEncoder.encode(request.password()),
                Role.USER);
        User savedUser = userRepository.save(user);
        return new RegisterResponse(savedUser.getId(), savedUser.getUsername(), savedUser.getRole());
    }
}
