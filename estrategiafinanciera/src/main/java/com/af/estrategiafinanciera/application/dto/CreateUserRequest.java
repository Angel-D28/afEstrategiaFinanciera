package com.af.estrategiafinanciera.application.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateUserRequest(
        @NotBlank(message = "El nombre es obligatorio")
        String name,

        @NotBlank(message = "El correo electronico es obligatorio")
        @Email(message = "El email no tiene formato válido")
        String email,

        @NotBlank(message = "La contraseña es obligatoia")
        @Size(min = 8, message = "La contraseña debe tener minimo 8 caracteres")
        String password
){}


