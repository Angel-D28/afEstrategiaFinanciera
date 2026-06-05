package com.af.estrategiafinanciera.infrastructure.adapter.out.persistence;

import com.af.estrategiafinanciera.domain.model.Subscription;
import com.af.estrategiafinanciera.infrastructure.adapter.out.persistence.entity.SubscriptionEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SubscriptionMapper {

    private final UserMapper userMapper;
    private final PlanMapper planMapper;

    public Subscription toDomain(SubscriptionEntity entity) {
        return new Subscription(
                entity.getId(),
                userMapper.toDomain(entity.getUser()),
                planMapper.toDomain(entity.getPlan()),
                entity.getStatus(),
                entity.getStartDate(),
                entity.getEndDate(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    public SubscriptionEntity toEntity(Subscription subscription) {
        return SubscriptionEntity.builder()
                .id(subscription.getId())
                .user(userMapper.toEntity(subscription.getUser()))
                .plan(planMapper.toEntity(subscription.getPlan()))
                .status(subscription.getStatus())
                .startDate(subscription.getStartDate())
                .endDate(subscription.getEndDate())
                .createdAt(subscription.getCreatedAt())
                .updatedAt(subscription.getUpdatedAt())
                .build();
    }
}