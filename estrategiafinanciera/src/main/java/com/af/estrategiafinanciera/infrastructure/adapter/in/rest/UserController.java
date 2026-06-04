package com.af.estrategiafinanciera.infrastructure.adapter.in.rest;

import com.af.estrategiafinanciera.application.dto.CreateUserRequest;
import com.af.estrategiafinanciera.application.dto.UpdateUserStatusRequest;
import com.af.estrategiafinanciera.application.dto.UserResponse;
import com.af.estrategiafinanciera.domain.model.User;
import com.af.estrategiafinanciera.domain.port.in.GetUserUseCase;
import com.af.estrategiafinanciera.domain.port.in.RegisterUserUseCase;
import com.af.estrategiafinanciera.domain.port.in.UpdateUserStatusUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final RegisterUserUseCase registerUserUseCase;
    private final UpdateUserStatusUseCase updateUserStatusUseCase;
    private final GetUserUseCase getUserUseCase;

    //Publico
    // POST /api/users/register
    @PostMapping("/register")
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
    public ResponseEntity<UserResponse> getById(@PathVariable Long id){
        User user = getUserUseCase.getById(id);
        return ResponseEntity.ok(toResponse(user));
    }

    // PATCH /api/users/{id}/status
    @PatchMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponse> updateStatus(
            @PathVariable Long id,
            @Valid @RequestBody UpdateUserStatusRequest request){
        User user = updateUserStatusUseCase.updateStatus(id, request.status());
        return ResponseEntity.ok(toResponse(user));
    }


    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
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
