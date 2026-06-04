package com.af.estrategiafinanciera.application.dto;

import com.af.estrategiafinanciera.domain.model.PlanStatus;
import jakarta.validation.constraints.NotNull;

public record UpdatePlanStatusRequest(
        @NotNull(message = "El estado es obligatorio")
        PlanStatus status
) {}
