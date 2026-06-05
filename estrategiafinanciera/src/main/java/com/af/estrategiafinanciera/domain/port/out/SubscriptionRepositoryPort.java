package com.af.estrategiafinanciera.domain.port.out;

import com.af.estrategiafinanciera.domain.model.Subscription;

import java.util.List;
import java.util.Optional;

public interface SubscriptionRepositoryPort {
    Subscription save(Subscription subscription);
    Optional<Subscription> findByid(Long id);
    List<Subscription> findAll();
    List<Subscription> findAllByUserId(Long userId);
    boolean existsActiveSubscriptionByUserIdAndPlanId(Long userId, Long planId);
    void deleteById(Long id);
}
