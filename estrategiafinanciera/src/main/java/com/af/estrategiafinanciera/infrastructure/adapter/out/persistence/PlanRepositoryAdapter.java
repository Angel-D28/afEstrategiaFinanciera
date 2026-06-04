package com.af.estrategiafinanciera.infrastructure.adapter.out.persistence;

import com.af.estrategiafinanciera.domain.model.Plan;
import com.af.estrategiafinanciera.domain.model.PlanStatus;
import com.af.estrategiafinanciera.domain.port.out.PlanRepositoryPort;
import com.af.estrategiafinanciera.infrastructure.adapter.out.persistence.repository.PlanJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PlanRepositoryAdapter implements PlanRepositoryPort {

    private final PlanJpaRepository jpaRepository;
    private final PlanMapper planMapper;

    @Override
    public Plan save(Plan plan){
        var entity = planMapper.toEntity(plan);
        var saved = jpaRepository.save(entity);
        return planMapper.toDomain(saved);
    }

    @Override
    public Optional<Plan> findByid(Long id) {
        return jpaRepository.findById(id)
                .map(planMapper::toDomain);
    }

    @Override
    public List<Plan> findAll(){
        return jpaRepository.findAll()
                .stream()
                .map(planMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Plan> findAllByStatus(PlanStatus status) {
        return jpaRepository.findAllByStatus(status)
                .stream()
                .map(planMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsByName(String name) {
        return jpaRepository.existsByName(name);
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }
}
