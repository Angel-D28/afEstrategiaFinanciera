package com.af.estrategiafinanciera.domain.port.in;

import com.af.estrategiafinanciera.domain.model.Subscription;
import com.af.estrategiafinanciera.domain.model.SubscriptionStatus;

public interface UpdateSubscriptionStatusUseCase {
    Subscription updateStatus(Long subscriptionId , SubscriptionStatus status);
}
