package com.af.estrategiafinanciera.infrastructure.adapter.out.persistence.repository;

import com.af.estrategiafinanciera.domain.model.Payment;
import com.af.estrategiafinanciera.domain.port.out.PaymentRepositoryPort;
import com.af.estrategiafinanciera.infrastructure.adapter.out.persistence.PaymentMapper;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PaymentRepositoryAdapter implements PaymentRepositoryPort {

    private final PaymentJpaRepository jpaRepository;
    private final PaymentMapper paymentMapper;

    @Override
    public Payment save(Payment payment){
        var entity = paymentMapper.toEntity(payment);
        var saved = jpaRepository.save(entity);
        return paymentMapper.toDomain(saved);
    }

    @Override
    public Optional<Payment> findById(Long id) {
        return jpaRepository.findById(id)
                .map(paymentMapper::toDomain);
    }

    @Override
    public List<Payment> findAll() {
        return jpaRepository.findAll()
                .stream()
                .map(paymentMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Payment> findAllBySubscriptionId(Long subscriptionId) {
        return jpaRepository.findAllBySubscriptionId(subscriptionId)
                .stream()
                .map(paymentMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }
}
