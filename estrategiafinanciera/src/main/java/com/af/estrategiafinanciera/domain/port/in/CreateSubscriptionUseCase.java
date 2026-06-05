package com.af.estrategiafinanciera.domain.port.in;

import com.af.estrategiafinanciera.domain.model.Subscription;

public interface CreateSubscriptionUseCase {
    Subscription subscribe(Long userId, Long planId);
}
