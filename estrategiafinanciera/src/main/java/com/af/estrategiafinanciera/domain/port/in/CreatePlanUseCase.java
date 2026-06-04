package com.af.estrategiafinanciera.domain.port.in;

import com.af.estrategiafinanciera.domain.model.Plan;

import java.math.BigDecimal;
import java.util.List;

public interface CreatePlanUseCase {
    Plan create(String name, String description, BigDecimal price,
                Integer durationMonths, List<String> features);
}
