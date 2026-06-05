package com.af.estrategiafinanciera.infrastructure.adapter.out.persistence;

import com.af.estrategiafinanciera.domain.model.Subscription;
import com.af.estrategiafinanciera.domain.port.out.SubscriptionRepositoryPort;
import com.af.estrategiafinanciera.infrastructure.adapter.out.persistence.repository.SubscriptionJpaRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SubscriptionRepositoryAdapter implements SubscriptionRepositoryPort {

    private final SubscriptionJpaRepository subscriptionJpaRepository;
    private final SubscriptionMapper subscriptionMapper;

    @Override
    public Subscription save(Subscription subscription) {
        var entity = subscriptionMapper.toEntity(subscription);
        var saved = subscriptionJpaRepository.save(entity);
        return subscriptionMapper.toDomain(saved);
    }

    @Override
    public Optional<Subscription> findByid(Long id) {
        return subscriptionJpaRepository.findById(id)
                .map(subscriptionMapper::toDomain);
    }

    @Override
    public List<Subscription> findAll() {
        return subscriptionJpaRepository.findAll()
                .stream()
                .map(subscriptionMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Subscription> findAllByUserId(Long userId) {
        return subscriptionJpaRepository.findAllByUserId(userId)
                .stream()
                .map(subscriptionMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsActiveSubscriptionByUserIdAndPlanId(Long userId, Long planId) {
        return subscriptionJpaRepository.existsActiveSubscriptionByUserIdAndPlanId(userId, planId);
    }

    @Override
    public void deleteById(Long id) {
        subscriptionJpaRepository.deleteById(id);
    }
}
