package com.af.estrategiafinanciera.application.dto;

import com.af.estrategiafinanciera.domain.model.Role;
import com.af.estrategiafinanciera.domain.model.UserStatus;

import java.time.LocalDateTime;

public record UserResponse(
   Long id,
   String name,
   String email,
   Role role,
   UserStatus status,
   LocalDateTime createdAt
) {}
