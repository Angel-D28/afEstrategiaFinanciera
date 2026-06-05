package com.af.estrategiafinanciera.domain.port.in;

import com.af.estrategiafinanciera.domain.model.Payment;

import java.util.List;

public interface GetPaymentUseCase {
    Payment getByid(Long id);
    List<Payment> getAll();
    List<Payment> geyAllBySubscription(Long subscriptioinId);
}
