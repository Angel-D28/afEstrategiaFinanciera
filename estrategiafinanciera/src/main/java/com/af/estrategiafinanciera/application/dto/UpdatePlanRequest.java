package com.af.estrategiafinanciera.application.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.List;

public record UpdatePlanRequest(
        @NotBlank(message = "El nombre es obligatorio")
        String name,

        @NotBlank(message = "La descripción es obligatoria")
        String description,

        @NotNull(message = "El precio es obligatorio")
        @DecimalMin(value = "0.0", message = "El precio no puede ser negativo")
        BigDecimal price,

        @NotNull(message = "La duración es obligatoria")
        @Min(value = 1, message = "La duración mínima es 1 mes")
        Integer durationMonths,

        @NotEmpty(message = "Debe tener al menos una característica")
        List<String> features
) {}
