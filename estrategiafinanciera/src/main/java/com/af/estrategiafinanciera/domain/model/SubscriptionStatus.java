package com.af.estrategiafinanciera.domain.model;

public enum SubscriptionStatus {
    PENDING,    // Inscripción creada, esperando pago
    ACTIVE,     // Suscripción activa y en curso
    PAUSED,     // Pausada temporalmente por el admin
    CANCELLED,  // Cancelada definitivamente
    EXPIRED     // Venció por fecha
}
