package com.af.estrategiafinanciera.application.dto;

import com.af.estrategiafinanciera.domain.model.PaymentMethod;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CreatePaymentRequest(
        @NotNull(message = "La suscripción es obligatoria")
        Long subscriptionId,

        @NotNull(message = "El monto es obligatorio")
        @DecimalMin(value = "0.01", message = "El monto debe ser mayor a cero")
        BigDecimal amount,

        @NotNull(message = "El método de pago es obligatorio")
        PaymentMethod method,

        String reference,
        String notes
) {}
