package com.af.estrategiafinanciera.application.dto;

import java.time.LocalDateTime;

public record ErrorResponse (
        int status,
        String error,
        String messag,
        LocalDateTime timestamp
){}
