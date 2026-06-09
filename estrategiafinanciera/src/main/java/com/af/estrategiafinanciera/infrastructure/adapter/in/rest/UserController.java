package com.af.estrategiafinanciera.infrastructure.adapter.in.rest;

import com.af.estrategiafinanciera.application.dto.CreateUserRequest;
import com.af.estrategiafinanciera.application.dto.UpdateUserStatusRequest;
import com.af.estrategiafinanciera.application.dto.UserResponse;
import com.af.estrategiafinanciera.domain.model.User;
import com.af.estrategiafinanciera.domain.port.in.GetUserUseCase;
import com.af.estrategiafinanciera.domain.port.in.RegisterUserUseCase;
import com.af.estrategiafinanciera.domain.port.in.UpdateUserStatusUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "Usuarios", description = "Gestión de usuarios del sistema")
public class UserController {

    private final RegisterUserUseCase registerUserUseCase;
    private final UpdateUserStatusUseCase updateUserStatusUseCase;
    private final GetUserUseCase getUserUseCase;

    //Publico
    // POST /api/users/register
    @PostMapping("/register")
    @Operation(summary = "Registrar usuario",
            description = "Registro público de nuevos clientes")
    public ResponseEntity<UserResponse> register (@Valid @RequestBody CreateUserRequest request){
        User user = registerUserUseCase.register(
                request.name(),
                request.email(),
                request.password()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(user));
    }

    //Solo Admin
    // GET /api/users
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Listar usuarios",
            description = "Solo ADMIN — retorna todos los usuarios")
    public ResponseEntity<List<UserResponse>> getAll(){
        List<UserResponse> users = getUserUseCase.getAll()
                .stream()
                .map(this::toResponse)
                .toList();
        return ResponseEntity.ok(users);
    }

    // GET /api/users/{id}
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Buscar usuario por ID",
            description = "Solo ADMIN")
    public ResponseEntity<UserResponse> getById(@PathVariable Long id){
        User user = getUserUseCase.getById(id);
        return ResponseEntity.ok(toResponse(user));
    }

    @GetMapping("/role/{role}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Buscar usuarios por rol",
            description = "Solo ADMIN — valores: ADMIN, AGENT, CLIENT")
    public ResponseEntity<List<UserResponse>> getAllByRole(@PathVariable String role) {
        List<UserResponse> users = getUserUseCase.getAllByRole(role)
                .stream()
                .map(this::toResponse)
                .toList();
        return ResponseEntity.ok(users);
    }

    // PATCH /api/users/{id}/status
    @PatchMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Cambiar estado de usuario",
            description = "Solo ADMIN — valores: ACTIVE, INACTIVE, SUSPENDED")
    public ResponseEntity<UserResponse> updateStatus(
            @PathVariable Long id,
            @Valid @RequestBody UpdateUserStatusRequest request){
        User user = updateUserStatusUseCase.updateStatus(id, request.status());
        return ResponseEntity.ok(toResponse(user));
    }


    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Mi perfil",
            description = "Retorna el perfil del usuario autenticado")
    public ResponseEntity<UserResponse> getMyProfile(
            org.springframework.security.core.Authentication authentication){
        User user = getUserUseCase.getByEmail(authentication.getName());
        return ResponseEntity.ok(toResponse(user));
    }


    // Método privado para convertir User → UserResponse
    private UserResponse toResponse(User user){
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole(),
                user.getStatus(),
                user.getCreatedAt()
        );
    }
}
