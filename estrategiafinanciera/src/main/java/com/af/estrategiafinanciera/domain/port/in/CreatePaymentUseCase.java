package com.af.estrategiafinanciera.domain.port.in;

import com.af.estrategiafinanciera.domain.model.Payment;
import com.af.estrategiafinanciera.domain.model.PaymentMethod;

import java.math.BigDecimal;

public interface CreatePaymentUseCase {
    Payment registerPayment(Long subscriptionId, BigDecimal amount,
                            PaymentMethod method, String reference,
                            String notes);
}
