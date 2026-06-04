package com.af.estrategiafinanciera.domain.port.in;

import com.af.estrategiafinanciera.domain.model.Plan;

import java.util.List;

public interface GetPlanUseCase {
    Plan getByid(Long id);
    List<Plan> getAll();
    List<Plan> getAllActive();;
}
