package com.af.estrategiafinanciera.domain.service;

import com.af.estrategiafinanciera.domain.exception.DuplicateResourceException;
import com.af.estrategiafinanciera.domain.exception.InvalidOperationException;
import com.af.estrategiafinanciera.domain.exception.ResourceNotFoundException;
import com.af.estrategiafinanciera.domain.model.Plan;
import com.af.estrategiafinanciera.domain.model.PlanStatus;
import com.af.estrategiafinanciera.domain.port.in.CreatePlanUseCase;
import com.af.estrategiafinanciera.domain.port.in.GetPlanUseCase;
import com.af.estrategiafinanciera.domain.port.in.UpdatePlanUseCase;
import com.af.estrategiafinanciera.domain.port.out.PlanRepositoryPort;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class PlanService implements CreatePlanUseCase, GetPlanUseCase, UpdatePlanUseCase {

    private final PlanRepositoryPort planRepositoryPort;

    public PlanService(PlanRepositoryPort planRepositoryPort) {
        this.planRepositoryPort = planRepositoryPort;
    }
//------------CreatePlanUseCase---------
    @Override
    public Plan create(String name, String description, BigDecimal price,
                       Integer durationMonths, List<String> features){
        if (planRepositoryPort.existsByName(name)){
            throw new DuplicateResourceException("plan", "nombre", name);
        }
        if (price.compareTo(BigDecimal.ZERO) < 0){
            throw new InvalidOperationException("El precio no puede ser negativo");
        }
        if (durationMonths < 1){
            throw new InvalidOperationException("La duracion minima es 1 mes");
        }
        Plan plan = new Plan();
        plan.setName(name);
        plan.setDescription(description);
        plan.setPrice(price);
        plan.setDurationMonths(durationMonths);
        plan.setFeatures(features);
        plan.setStatus(PlanStatus.DRAFT);
        plan.setCreatedAt(LocalDateTime.now());
        plan.setUpdatedAt(LocalDateTime.now());

        return planRepositoryPort.save(plan);
    }
    //---------UpdatePlanUseCase---------------------------------
    @Override
    public Plan update(Long id, String name, String description,
                      BigDecimal price, Integer durationMonths,
                      List<String> features){
        Plan plan = planRepositoryPort.findByid(id)
                .orElseThrow(()-> new ResourceNotFoundException("Plan", id));

        plan.setName(name);
        plan.setDescription(description);
        plan.setPrice(price);
        plan.setDurationMonths(durationMonths);
        plan.setFeatures(features);
        plan.setUpdatedAt(LocalDateTime.now());

        return planRepositoryPort.save(plan);
    }

    @Override
    public Plan updateStatus(Long id, PlanStatus status){
        Plan plan = planRepositoryPort.findByid(id)
                .orElseThrow(() -> new ResourceNotFoundException("Plan", id));

        switch (status){
            case ACTIVE -> plan.activate();
            case INACTIVE -> plan.deactivate();
            case DRAFT -> plan.draft();
            default -> throw new InvalidOperationException(
                    "Estado no válido: " + status);
        }
        return planRepositoryPort.save(plan);
    }

    //---GetPlanUseCase-----------

    @Override
    public Plan getByid(Long id) {
        return planRepositoryPort.findByid(id)
                .orElseThrow(() -> new ResourceNotFoundException("Plan", id));
    }

    @Override
    public List<Plan> getAll() {
        return planRepositoryPort.findAll();
    }

    @Override
    public List<Plan> getAllActive() {
        return planRepositoryPort.findAllByStatus(PlanStatus.ACTIVE);
    }

}
