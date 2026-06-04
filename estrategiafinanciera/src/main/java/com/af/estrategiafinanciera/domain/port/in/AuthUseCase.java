package com.af.estrategiafinanciera.domain.port.in;

import com.af.estrategiafinanciera.application.dto.AuthResponse;
import com.af.estrategiafinanciera.application.dto.LoginRequest;

public interface AuthUseCase {
    AuthResponse login(LoginRequest request);
}
