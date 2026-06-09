package com.af.estrategiafinanciera.infrastructure.adapter.in.rest;

import com.af.estrategiafinanciera.application.dto.CreatePaymentRequest;
import com.af.estrategiafinanciera.application.dto.PaymentResponse;
import com.af.estrategiafinanciera.application.dto.UpdatePaymentStatusRequest;
import com.af.estrategiafinanciera.domain.model.Payment;
import com.af.estrategiafinanciera.domain.port.in.CreatePaymentUseCase;
import com.af.estrategiafinanciera.domain.port.in.GetPaymentUseCase;
import com.af.estrategiafinanciera.domain.port.in.UpdatePaymentStatusUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final CreatePaymentUseCase createPaymentUseCase;
    private final UpdatePaymentStatusUseCase updatePaymentStatusUseCase;
    private final GetPaymentUseCase getPaymentUseCase;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Tag(name = "Pagos", description = "Gestión de pagos de suscripciones")
    @Operation(summary = "Registrar pago",
            description = "Solo ADMIN — registra un pago en estado PENDING")
    public ResponseEntity<PaymentResponse> register(
            @Valid @RequestBody CreatePaymentRequest request){
        Payment payment = createPaymentUseCase.registerPayment(
                request.subscriptionId(),
                request.amount(),
                request.method(),
                request.reference(),
                request.notes()
        );
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(toResponse(payment));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Listar pagos",
            description = "Solo ADMIN")
    public ResponseEntity<List<PaymentResponse>> getAll(){
        List<PaymentResponse> payments = getPaymentUseCase.getAll()
                .stream()
                .map(this::toResponse)
                .toList();

        return ResponseEntity.ok(payments);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Buscar pago por ID",
            description = "Solo ADMIN")
    public ResponseEntity<PaymentResponse> getById(@PathVariable Long id){
        Payment payment = getPaymentUseCase.getByid(id);
        return ResponseEntity.ok(toResponse(payment));
    }

    @GetMapping("/subscription/{subscriptionId}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Pagos por suscripción",
            description = "Solo ADMIN")
    public ResponseEntity<List<PaymentResponse>> getBySubscription(
            @PathVariable Long subscriptionId) {
        List<PaymentResponse> payments = getPaymentUseCase
                .geyAllBySubscription(subscriptionId)
                .stream()
                .map(this::toResponse)
                .toList();
        return ResponseEntity.ok(payments);
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Cambiar estado del pago",
            description = "Solo ADMIN — valores: COMPLETED, FAILED, REFUNDED")
    public ResponseEntity<PaymentResponse> updateStatus(
            @PathVariable Long id,
            @Valid @RequestBody UpdatePaymentStatusRequest request) {
        Payment payment = updatePaymentStatusUseCase
                .updateStatus(id, request.status());
        return ResponseEntity.ok(toResponse(payment));
    }


    private PaymentResponse toResponse(Payment payment) {
        return new PaymentResponse(
                payment.getId(),
                payment.getSubscription().getId(),
                payment.getSubscription().getUser().getName(),
                payment.getSubscription().getPlan().getName(),
                payment.getAmount(),
                payment.getMethod(),
                payment.getStatus(),
                payment.getReference(),
                payment.getNotes(),
                payment.getPaymentDate()
        );
    }

}
