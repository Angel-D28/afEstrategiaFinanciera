package com.af.estrategiafinanciera.application.dto;

import com.af.estrategiafinanciera.domain.model.SubscriptionStatus;
import jakarta.validation.constraints.NotNull;

public record UpdateSubscriptionStatusRequest(
        @NotNull(message = "El estado de suscripcion es necesario")
        SubscriptionStatus status
) {}
