package com.af.estrategiafinanciera.domain.port.out;

import com.af.estrategiafinanciera.domain.model.Plan;
import com.af.estrategiafinanciera.domain.model.PlanStatus;

import java.util.List;
import java.util.Optional;

public interface PlanRepositoryPort {
    Plan save(Plan plan);
    Optional<Plan> findByid(Long id);
    List<Plan> findAll();
    List<Plan> findAllByStatus(PlanStatus status);
    boolean existsByName(String name);
    void deleteById(Long id);
}
