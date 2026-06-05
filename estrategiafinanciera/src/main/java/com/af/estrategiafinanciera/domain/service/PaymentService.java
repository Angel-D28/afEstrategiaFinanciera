package com.af.estrategiafinanciera.domain.service;

import com.af.estrategiafinanciera.domain.model.*;
import com.af.estrategiafinanciera.domain.port.in.CreatePaymentUseCase;
import com.af.estrategiafinanciera.domain.port.in.GetPaymentUseCase;
import com.af.estrategiafinanciera.domain.port.in.UpdatePaymentStatusUseCase;
import com.af.estrategiafinanciera.domain.port.out.PaymentRepositoryPort;
import com.af.estrategiafinanciera.domain.port.out.SubscriptionRepositoryPort;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class PaymentService implements CreatePaymentUseCase, UpdatePaymentStatusUseCase, GetPaymentUseCase {

    private final PaymentRepositoryPort paymentRepositoryPort;
    private final SubscriptionRepositoryPort subscriptionRepositoryPort;

    public PaymentService(PaymentRepositoryPort paymentRepositoryPort, SubscriptionRepositoryPort subscriptionRepositoryPort) {
        this.paymentRepositoryPort = paymentRepositoryPort;
        this.subscriptionRepositoryPort = subscriptionRepositoryPort;
    }

    @Override
    public Payment registerPayment(Long subscriptionId, BigDecimal amount, PaymentMethod method, String reference, String notes) {
        Subscription subscription = subscriptionRepositoryPort.findByid(subscriptionId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Suscripcion no encontrada con ID " + subscriptionId
                ));
        if (amount.compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException(
                    "El monto debe ser mayor a 0"
            );
        }

        if(subscription.getStatus() == SubscriptionStatus.CANCELLED){
            throw new IllegalArgumentException(
                    "No se puede registrar pago para una suscripción cancelada"
            );
        }

        Payment payment = new Payment();
        payment.setSubscription(subscription);
        payment.setAmount(amount);
        payment.setMethod(method);
        payment.setStatus(PaymentStatus.PENDING);
        payment.setReference(reference);
        payment.setNotes(notes);
        payment.setPaymentDate(LocalDateTime.now());
        payment.setCreatedAt(LocalDateTime.now());
        payment.setCreatedAt(LocalDateTime.now());

        return paymentRepositoryPort.save(payment);
    }

    @Override
    public Payment updateStatus(Long paymentId, PaymentStatus status) {
        Payment payment = paymentRepositoryPort.findById(paymentId)
                .orElseThrow(()-> new IllegalArgumentException(
                        "No existe un pago con id " + paymentId
                ));
        switch (status){
            case FAILED -> payment.fail();
            case COMPLETED -> payment.complete();
            case REFUNDED -> payment.refund();
            default -> throw new IllegalArgumentException(
                    "Estado no Valido : "+ status
            );
        }
        return paymentRepositoryPort.save(payment);
    }

    @Override
    public Payment getByid(Long id) {
        return paymentRepositoryPort.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Pago no encontrado con id: " + id
                ));
    }

    @Override
    public List<Payment> getAll() {
        return paymentRepositoryPort.findAll();
    }

    @Override
    public List<Payment> geyAllBySubscription(Long subscriptionId) {
        return paymentRepositoryPort.findAllBySubscriptionId(subscriptionId);
    }
}
