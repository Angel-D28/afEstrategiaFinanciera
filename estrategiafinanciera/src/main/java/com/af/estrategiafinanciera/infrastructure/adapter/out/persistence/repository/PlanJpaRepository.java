package com.af.estrategiafinanciera.infrastructure.adapter.out.persistence.repository;

import com.af.estrategiafinanciera.domain.model.PlanStatus;
import com.af.estrategiafinanciera.infrastructure.adapter.out.persistence.entity.PlanEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlanJpaRepository extends JpaRepository<PlanEntity, Long> {
    List<PlanEntity> findAllByStatus(PlanStatus status);
    boolean existsByName(String name);
}
