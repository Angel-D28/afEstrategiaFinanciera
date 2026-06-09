package com.af.estrategiafinanciera.domain.service;

import com.af.estrategiafinanciera.domain.exception.DuplicateResourceException;
import com.af.estrategiafinanciera.domain.exception.InvalidOperationException;
import com.af.estrategiafinanciera.domain.exception.ResourceNotFoundException;
import com.af.estrategiafinanciera.domain.model.Role;
import com.af.estrategiafinanciera.domain.model.User;
import com.af.estrategiafinanciera.domain.model.UserStatus;
import com.af.estrategiafinanciera.domain.port.in.GetUserUseCase;
import com.af.estrategiafinanciera.domain.port.in.RegisterUserUseCase;
import com.af.estrategiafinanciera.domain.port.in.UpdateUserStatusUseCase;
import com.af.estrategiafinanciera.domain.port.out.PasswordEncoderPort;
import com.af.estrategiafinanciera.domain.port.out.UserRepositoryPort;

import java.time.LocalDateTime;
import java.util.List;

public class UserService implements RegisterUserUseCase, UpdateUserStatusUseCase, GetUserUseCase {

    private final UserRepositoryPort userRepositoryPort;
    private final PasswordEncoderPort passwordEncoderPort;

    public UserService(UserRepositoryPort userRepositoryPort,
                       PasswordEncoderPort passwordEncoderPort){
        this.userRepositoryPort = userRepositoryPort;
        this.passwordEncoderPort = passwordEncoderPort;
    }

    //RegisterUserUseCase

    @Override
    public User register (String name , String email, String password){
        //No permitira email duplicados
        if (userRepositoryPort.existsByEmail(email)){
            throw new DuplicateResourceException("usuario", "email", email);
        }
        User newUser = new User();
        newUser.setName(name);
        newUser.setEmail(email);
        newUser.setPassword(passwordEncoderPort.encode(password));
        newUser.setRole(Role.CLIENT);
        newUser.setStatus(UserStatus.PENDING);
        newUser.setCreatedAt(LocalDateTime.now());
        newUser.setUpdatedAt(LocalDateTime.now());

        return userRepositoryPort.save(newUser);
    }

    //UpdateUserStatusUsecase

    @Override
    public User updateStatus(Long userId , UserStatus newStatus){
        User user = userRepositoryPort.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", userId));

        switch (newStatus){
            case ACTIVE -> user.activate();
            case INACTIVE -> user.deactivate();
            case SUSPENDED -> user.suspended();
            default -> throw new InvalidOperationException(
                    "Estado no válido: " + newStatus);
        }
        return userRepositoryPort.save(user);
    }

    //GetUserUseCase
    @Override
    public User getById(Long id){
        return userRepositoryPort.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Usuario", id));
    }

    @Override
    public User getByEmail(String email){
        return userRepositoryPort.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Usuario", "email", email));
    }

    @Override
    public List<User> getAll(){
        return userRepositoryPort.findAll();
    }

    @Override
    public List<User> getAllByRole(String role){
        Role enumRole = Role.valueOf(role.toUpperCase());
        return userRepositoryPort.findAllByRole(enumRole);
    }
}
