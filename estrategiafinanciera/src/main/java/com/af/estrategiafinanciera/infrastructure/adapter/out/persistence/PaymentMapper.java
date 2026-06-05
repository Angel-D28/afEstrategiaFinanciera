package com.af.estrategiafinanciera.infrastructure.adapter.out.persistence;

import com.af.estrategiafinanciera.domain.model.Payment;
import com.af.estrategiafinanciera.infrastructure.adapter.out.persistence.entity.PaymentEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentMapper {

    private final SubscriptionMapper subscriptionMapper;

    public Payment toDomain(PaymentEntity entity){
        return new Payment(
                entity.getId(),
                subscriptionMapper.toDomain(entity.getSubscription()),
                entity.getAmount(),
                entity.getMethod(),
                entity.getStatus(),
                entity.getReference(),
                entity.getNotes(),
                entity.getPaymentDate(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    public PaymentEntity toEntity(Payment payment) {
        return PaymentEntity.builder()
                .id(payment.getId())
                .subscription(subscriptionMapper.toEntity(
                        payment.getSubscription()))
                .amount(payment.getAmount())
                .method(payment.getMethod())
                .status(payment.getStatus())
                .reference(payment.getReference())
                .notes(payment.getNotes())
                .paymentDate(payment.getPaymentDate())
                .createdAt(payment.getCreatedAt())
                .updatedAt(payment.getUpdatedAt())
                .build();
    }
}
