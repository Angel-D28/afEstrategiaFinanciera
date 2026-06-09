package com.af.estrategiafinanciera.infrastructure.adapter.in.rest;

import com.af.estrategiafinanciera.application.dto.AuthResponse;
import com.af.estrategiafinanciera.application.dto.LoginRequest;
import com.af.estrategiafinanciera.domain.port.in.AuthUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Autenticacion" , description = "Endpoints de login y registro")
public class AuthController {

    private final AuthUseCase authUseCase;

    @PostMapping("/login")
    @Operation(summary = "Login" , description = "Autentica un usuario y retorna un JWT ")
    public ResponseEntity<AuthResponse> login(@Valid  @RequestBody LoginRequest request){
        return ResponseEntity.ok(authUseCase.login(request));
    }
}
