package com.af.estrategiafinanciera.domain.service;

import com.af.estrategiafinanciera.domain.model.Plan;
import com.af.estrategiafinanciera.domain.model.Subscription;
import com.af.estrategiafinanciera.domain.model.SubscriptionStatus;
import com.af.estrategiafinanciera.domain.model.User;
import com.af.estrategiafinanciera.domain.port.in.CreateSubscriptionUseCase;
import com.af.estrategiafinanciera.domain.port.in.GetSubscriptionUseCase;
import com.af.estrategiafinanciera.domain.port.in.UpdateSubscriptionStatusUseCase;
import com.af.estrategiafinanciera.domain.port.out.PlanRepositoryPort;
import com.af.estrategiafinanciera.domain.port.out.SubscriptionRepositoryPort;
import com.af.estrategiafinanciera.domain.port.out.UserRepositoryPort;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class SubscriptionService implements CreateSubscriptionUseCase,
        UpdateSubscriptionStatusUseCase, GetSubscriptionUseCase {

    private final SubscriptionRepositoryPort subscriptionRepositoryPort;
    private final UserRepositoryPort userRepositoryPort;
    private final PlanRepositoryPort planRepositoryPort;

    public SubscriptionService(SubscriptionRepositoryPort subscriptionRepositoryPort,
                               UserRepositoryPort userRepositoryPort, PlanRepositoryPort planRepositoryPort) {
        this.subscriptionRepositoryPort = subscriptionRepositoryPort;
        this.userRepositoryPort = userRepositoryPort;
        this.planRepositoryPort = planRepositoryPort;
    }

    // ── CreateSubscriptionUseCase ───────────────────────────────

    @Override
    public Subscription subscribe(Long userId, Long planId) {
        User user = userRepositoryPort.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "El usuario no encotrado con id " + userId
                ));

        if (!user.isActive()){
            throw new IllegalArgumentException(
                    "El usuario " + user.getName() + "no esta activo"
            );
        }

        Plan plan = planRepositoryPort.findByid(planId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No se encontro plan con id " + planId
                ));

        if (!plan.isActive()){
            throw new IllegalArgumentException(
                    "El plan " + plan.getName() + " con id " + planId + " No esta activo en este momento"
            );
        }

        if (subscriptionRepositoryPort.existsActiveSubscriptionByUserIdAndPlanId(userId, planId)){
            throw new IllegalArgumentException(
                    "Ya tienes una subscripcion activa en este plan."
            );
        }

        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusMonths(plan.getDurationMonths());

        Subscription subscription = new Subscription();
        subscription.setUser(user);
        subscription.setPlan(plan);
        subscription.setStatus(SubscriptionStatus.PENDING);
        subscription.setStartDate(startDate);
        subscription.setEndDate(endDate);
        subscription.setCreatedAt(LocalDateTime.now());
        subscription.setUpdatedAt(LocalDateTime.now());

        return subscriptionRepositoryPort.save(subscription);
    }

    // ── UpdateSubscriptionStatusUseCase ────────────────────────


    @Override
    public Subscription updateStatus(Long subscriptionId, SubscriptionStatus status) {
        Subscription subscription = subscriptionRepositoryPort.findByid(subscriptionId)
                .orElseThrow(()-> new IllegalArgumentException(
                        "No se encontro Suscripcion con id " + subscriptionId
                ));
        switch (status){
            case ACTIVE -> subscription.activate();
            case PAUSED -> subscription.pause();
            case EXPIRED -> subscription.expire();
            case CANCELLED -> subscription.cancel();
            default -> throw new IllegalArgumentException(
                    "Estado no valido: " + status
            );
        }
        return subscriptionRepositoryPort.save(subscription);
    }

    // ── GetSubscriptionUseCase ──────────────────────────────────

    @Override
    public Subscription getById(Long id) {
        return subscriptionRepositoryPort.findByid(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No se encontro suscripcion con id " + id
                ));
    }

    @Override
    public List<Subscription> getAll() {
        return subscriptionRepositoryPort.findAll();
    }

    @Override
    public List<Subscription> getAllByUserId(Long userId) {
        return subscriptionRepositoryPort.findAllByUserId(userId);
    }
}
