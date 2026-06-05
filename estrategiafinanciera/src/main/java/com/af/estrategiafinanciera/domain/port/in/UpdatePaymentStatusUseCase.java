package com.af.estrategiafinanciera.domain.port.in;

import com.af.estrategiafinanciera.domain.model.Payment;
import com.af.estrategiafinanciera.domain.model.PaymentStatus;

public interface UpdatePaymentStatusUseCase {
    Payment updateStatus(Long paymentId, PaymentStatus status);
}
