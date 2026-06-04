package com.af.estrategiafinanciera.infrastructure.adapter.in.rest;

import com.af.estrategiafinanciera.application.dto.CreatePlanRequest;
import com.af.estrategiafinanciera.application.dto.PlanResponse;
import com.af.estrategiafinanciera.application.dto.UpdatePlanRequest;
import com.af.estrategiafinanciera.application.dto.UpdatePlanStatusRequest;
import com.af.estrategiafinanciera.domain.model.Plan;
import com.af.estrategiafinanciera.domain.model.PlanStatus;
import com.af.estrategiafinanciera.domain.port.in.CreatePlanUseCase;
import com.af.estrategiafinanciera.domain.port.in.GetPlanUseCase;
import com.af.estrategiafinanciera.domain.port.in.UpdatePlanUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/plans")
@RequiredArgsConstructor
public class PlanCotroller {
    private final CreatePlanUseCase createPlanUseCase;
    private final UpdatePlanUseCase updatePlanUseCase;
    private final GetPlanUseCase getPlanUseCase;

    @GetMapping("/active")
    public ResponseEntity<List<PlanResponse>> getAllActive(){
        List<PlanResponse> plans = getPlanUseCase.getAllActive()
                .stream()
                .map(this::toResponse)
                .toList();
        return ResponseEntity.ok(plans);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlanResponse> getById(@PathVariable Long id){
        Plan plan = getPlanUseCase.getByid(id);
        return ResponseEntity.ok(toResponse(plan));
    }

    // ── Solo ADMIN ───────────────────────────────────────────────

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<PlanResponse>> getAll(){
        List<PlanResponse> plans = getPlanUseCase.getAll()
                .stream()
                .map(this::toResponse)
                .toList();

        return ResponseEntity.ok(plans);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PlanResponse> create(
            @Valid @RequestBody CreatePlanRequest request
            ){
        Plan plan = createPlanUseCase.create(
                request.name(),
                request.description(),
                request.price(),
                request.durationMonths(),
                request.features()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(plan));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PlanResponse>update(
            @PathVariable Long id,
            @Valid @RequestBody UpdatePlanRequest request
            ){
        Plan plan = updatePlanUseCase.update(
                id,
                request.name(),
                request.description(),
                request.price(),
                request.durationMonths(),
                request.features()
        );
        return ResponseEntity.ok(toResponse(plan));
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PlanResponse> updateStatus(
            @PathVariable Long id,
            @Valid @RequestBody UpdatePlanStatusRequest request
            ){
        Plan plan = updatePlanUseCase.updateStatus(id, request.status());
        return ResponseEntity.ok(toResponse(plan));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PlanResponse> delete(@PathVariable Long id){
        updatePlanUseCase.updateStatus(id,
                PlanStatus.INACTIVE);
        return ResponseEntity.noContent().build();
    }


    // ── Mapper privado ───────────────────────────────────────────

    private PlanResponse toResponse(Plan plan) {
        return new PlanResponse(
                plan.getId(),
                plan.getName(),
                plan.getDescription(),
                plan.getPrice(),
                plan.getDurationMonths(),
                plan.getFeatures(),
                plan.getStatus(),
                plan.getCreatedAt()
        );
    }

}
