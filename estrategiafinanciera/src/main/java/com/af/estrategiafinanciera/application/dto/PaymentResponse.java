package com.af.estrategiafinanciera.application.dto;

import com.af.estrategiafinanciera.domain.model.PaymentMethod;
import com.af.estrategiafinanciera.domain.model.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PaymentResponse(
        Long id,
        Long subscriptionId,
        String userName,
        String planName,
        BigDecimal amount,
        PaymentMethod method,
        PaymentStatus status,
        String reference,
        String notes,
        LocalDateTime paymentDate
) {}
