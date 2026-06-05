package com.af.estrategiafinanciera.application.dto;

import jakarta.validation.constraints.NotNull;

public record CreateSubscriptionRequest(
   @NotNull(message = "El plan es obligatorio")
   Long planId
) {}
