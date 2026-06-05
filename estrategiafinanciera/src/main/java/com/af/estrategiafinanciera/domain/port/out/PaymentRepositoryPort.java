package com.af.estrategiafinanciera.domain.port.out;

import com.af.estrategiafinanciera.domain.model.Payment;

import java.util.List;
import java.util.Optional;

public interface PaymentRepositoryPort {
    Payment save(Payment payment);
    Optional<Payment> findById(Long id);
    List<Payment> findAll();
    List<Payment> findAllBySubscriptionId(Long subscriptionId);
    void deleteById(Long id);
}
