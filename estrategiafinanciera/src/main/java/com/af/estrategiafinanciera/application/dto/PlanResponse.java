package com.af.estrategiafinanciera.application.dto;

import com.af.estrategiafinanciera.domain.model.PlanStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record PlanResponse (
        Long id,
        String name,
        String description,
        BigDecimal price,
        Integer durationMonths,
        List<String> features,
        PlanStatus status,
        LocalDateTime createdAt
) {}
