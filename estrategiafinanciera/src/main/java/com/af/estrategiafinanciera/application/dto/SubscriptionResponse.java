package com.af.estrategiafinanciera.application.dto;

import com.af.estrategiafinanciera.domain.model.SubscriptionStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record SubscriptionResponse(
        Long id,
        Long userId,
        String userName,
        Long planId,
        String planName,
        SubscriptionStatus status,
        LocalDate startDate,
        LocalDate endDate,
        LocalDateTime createdAt
) {}
