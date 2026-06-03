package com.af.estrategiafinanciera.application.dto;

import com.af.estrategiafinanciera.domain.model.UserStatus;
import jakarta.validation.constraints.NotNull;

public record UpdateUserStatusRequest(
   @NotNull(message = "El estado es obligatorio")
   UserStatus status
) {}
