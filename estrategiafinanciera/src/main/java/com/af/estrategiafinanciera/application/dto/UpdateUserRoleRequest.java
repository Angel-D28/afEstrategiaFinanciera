package com.af.estrategiafinanciera.application.dto;

import com.af.estrategiafinanciera.domain.model.Role;
import jakarta.validation.constraints.NotNull;

public record UpdateUserRoleRequest (
        @NotNull(message = "El rol es obligatorio")
        Role role
)
{}
