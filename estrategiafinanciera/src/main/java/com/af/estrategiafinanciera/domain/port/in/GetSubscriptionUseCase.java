package com.af.estrategiafinanciera.domain.port.in;

import com.af.estrategiafinanciera.domain.model.Subscription;

import java.util.List;

public interface GetSubscriptionUseCase {
    Subscription getById(Long id);
    List<Subscription> getAll();
    List<Subscription> getAllByUserId(Long userId);

}
