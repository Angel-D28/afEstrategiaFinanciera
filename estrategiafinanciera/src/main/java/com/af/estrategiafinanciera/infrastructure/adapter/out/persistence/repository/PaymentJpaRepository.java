package com.af.estrategiafinanciera.infrastructure.adapter.out.persistence.repository;

import com.af.estrategiafinanciera.infrastructure.adapter.out.persistence.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentJpaRepository extends JpaRepository<PaymentEntity , Long> {
    List<PaymentEntity> findAllBySubscriptionId(Long subscriptionId);
}
