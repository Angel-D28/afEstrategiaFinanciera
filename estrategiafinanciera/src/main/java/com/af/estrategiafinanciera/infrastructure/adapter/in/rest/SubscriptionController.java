package com.af.estrategiafinanciera.infrastructure.adapter.in.rest;

import com.af.estrategiafinanciera.application.dto.CreateSubscriptionRequest;
import com.af.estrategiafinanciera.application.dto.SubscriptionResponse;
import com.af.estrategiafinanciera.application.dto.UpdateSubscriptionStatusRequest;
import com.af.estrategiafinanciera.domain.model.Subscription;
import com.af.estrategiafinanciera.domain.port.in.CreateSubscriptionUseCase;
import com.af.estrategiafinanciera.domain.port.in.GetSubscriptionUseCase;
import com.af.estrategiafinanciera.domain.port.in.GetUserUseCase;
import com.af.estrategiafinanciera.domain.port.in.UpdateSubscriptionStatusUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {

    private final CreateSubscriptionUseCase createSubscriptionUseCase;
    private final UpdateSubscriptionStatusUseCase updateSubscriptionStatusUseCase;
    private final GetSubscriptionUseCase getSubscriptionUseCase;
    private final GetUserUseCase getUserUseCase;

    // ── Cliente se suscribe ─────────────────────────────────────
    @PostMapping
    @PreAuthorize("hasAnyRole('CLIENT', 'ADMIN')")
    public ResponseEntity<SubscriptionResponse> subscribe(
            @Valid @RequestBody CreateSubscriptionRequest request,
            Authentication authentication ){
        var user = getUserUseCase.getByEmail(authentication.getName());

        Subscription subscription = createSubscriptionUseCase.subscribe(user.getId(), request.planId());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(toResponse(subscription));
    }

    // ── Cliente ve sus suscripciones ────────────────────────────
    @GetMapping("/my")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<SubscriptionResponse>> getMySubs(Authentication authentication){
        var user = getUserUseCase.getByEmail(authentication.getName());
        List<SubscriptionResponse> subs = getSubscriptionUseCase.getAllByUserId(user.getId())
                .stream()
                .map(this::toResponse)
                .toList();
        return ResponseEntity.ok(subs);
    }

    // ── Solo ADMIN ───────────────────────────────────────────────
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<SubscriptionResponse>> getAll(){
        List<SubscriptionResponse> subs = getSubscriptionUseCase.getAll().stream()
                .map(this::toResponse)
                .toList();

        return ResponseEntity.ok(subs);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SubscriptionResponse> getById(
            @PathVariable Long id
    ){
        Subscription sub = getSubscriptionUseCase.getById(id);
        return ResponseEntity.ok(toResponse(sub));
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SubscriptionResponse> updateStatus(
            @PathVariable Long id,
            @Valid @RequestBody UpdateSubscriptionStatusRequest request) {
        Subscription sub = updateSubscriptionStatusUseCase
                .updateStatus(id, request.status());
        return ResponseEntity.ok(toResponse(sub));
    }

    private SubscriptionResponse toResponse(Subscription sub) {
        return new SubscriptionResponse(
                sub.getId(),
                sub.getUser().getId(),
                sub.getUser().getName(),
                sub.getPlan().getId(),
                sub.getPlan().getName(),
                sub.getStatus(),
                sub.getStartDate(),
                sub.getEndDate(),
                sub.getCreatedAt()
        );
    }
}
