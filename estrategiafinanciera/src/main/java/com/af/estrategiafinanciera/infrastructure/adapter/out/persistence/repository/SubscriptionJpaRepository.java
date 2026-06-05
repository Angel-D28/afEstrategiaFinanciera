package com.af.estrategiafinanciera.infrastructure.adapter.out.persistence.repository;

import com.af.estrategiafinanciera.infrastructure.adapter.out.persistence.entity.SubscriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SubscriptionJpaRepository
        extends JpaRepository<SubscriptionEntity, Long> {
    List<SubscriptionEntity> findAllByUserId(Long userId);

    @Query("""
        SELECT COUNT(s) > 0 FROM SubscriptionEntity s
        WHERE s.user.id = :userId
        AND s.plan.id = :planId
        AND s.status IN ('PENDING', 'ACTIVE', 'PAUSED')
    """)
    boolean existsActiveSubscriptionByUserIdAndPlanId(
            @Param("userId") Long userId,
            @Param("planId") Long planId
    );
}
