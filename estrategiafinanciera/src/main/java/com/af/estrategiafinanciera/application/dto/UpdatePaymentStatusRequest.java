package com.af.estrategiafinanciera.application.dto;

import com.af.estrategiafinanciera.domain.model.PaymentStatus;
import jakarta.validation.constraints.NotNull;

public record UpdatePaymentStatusRequest(
        @NotNull(message = "El estado es obligatorio")
        PaymentStatus status
) {}
