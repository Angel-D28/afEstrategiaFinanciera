package com.af.estrategiafinanciera.domain.port.in;

import com.af.estrategiafinanciera.domain.model.Plan;
import com.af.estrategiafinanciera.domain.model.PlanStatus;

import java.math.BigDecimal;
import java.util.List;

public interface UpdatePlanUseCase {
    Plan update(Long id, String name, String description,
                BigDecimal price, Integer durationMonths,
                List<String> features);
    Plan updateStatus(Long id, PlanStatus status);
}
