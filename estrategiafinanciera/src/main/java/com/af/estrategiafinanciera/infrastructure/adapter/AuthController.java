package com.af.estrategiafinanciera.infrastructure.adapter;

import com.af.estrategiafinanciera.application.dto.AuthResponse;
import com.af.estrategiafinanciera.application.dto.LoginRequest;
import com.af.estrategiafinanciera.domain.port.in.AuthUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthUseCase authUseCase;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid  @RequestBody LoginRequest request){
        return ResponseEntity.ok(authUseCase.login(request));
    }
}
