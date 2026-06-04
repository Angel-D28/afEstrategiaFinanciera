package com.af.estrategiafinanciera.application.dto;

import com.af.estrategiafinanciera.domain.model.Role;

public record AuthResponse(
        String token,
        String name,
        String email,
        Role Role
) {}
