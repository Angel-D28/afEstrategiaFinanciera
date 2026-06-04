package com.af.estrategiafinanciera.infrastructure.adapter.out.persistence;

import com.af.estrategiafinanciera.domain.model.Plan;
import com.af.estrategiafinanciera.infrastructure.adapter.out.persistence.entity.PlanEntity;
import org.springframework.stereotype.Component;

@Component
public class PlanMapper {
    public Plan toDomain(PlanEntity entity){
        return new Plan(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getPrice(),
                entity.getDurationMonths(),
                entity.getFeatures(),
                entity.getStatus(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    public PlanEntity toEntity(Plan plan){
        return PlanEntity.builder()
                .id(plan.getId())
                .name(plan.getName())
                .description(plan.getDescription())
                .price(plan.getPrice())
                .durationMonths(plan.getDurationMonths())
                .features(plan.getFeatures())
                .status(plan.getStatus())
                .createdAt(plan.getCreatedAt())
                .updatedAt(plan.getUpdatedAt())
                .build();
    }

}
