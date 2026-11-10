package com.example.identityservice.auth;

import com.example.identityservice.user.Role;
import java.util.UUID;

public record RegisterResponse(UUID id, String username, Role role) {
}
