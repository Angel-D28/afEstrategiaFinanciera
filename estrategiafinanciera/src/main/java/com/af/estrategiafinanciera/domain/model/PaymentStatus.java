package com.af.estrategiafinanciera.domain.model;

public enum PaymentStatus {
    PENDING,    // Registrado pero no confirmado
    COMPLETED,  // Pago confirmado
    FAILED,     // Pago fallido
    REFUNDED    // Devuelto al cliente
}
