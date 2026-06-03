package com.af.estrategiafinanciera.domain.service;

import com.af.estrategiafinanciera.domain.model.Role;
import com.af.estrategiafinanciera.domain.model.User;
import com.af.estrategiafinanciera.domain.model.UserStatus;
import com.af.estrategiafinanciera.domain.port.in.GetUserUseCase;
import com.af.estrategiafinanciera.domain.port.in.RegisterUserUseCase;
import com.af.estrategiafinanciera.domain.port.in.UpdateUserStatusUseCase;
import com.af.estrategiafinanciera.domain.port.out.UserRepositoryPort;

import java.time.LocalDateTime;
import java.util.List;

public class UserService implements RegisterUserUseCase, UpdateUserStatusUseCase, GetUserUseCase {

    private final UserRepositoryPort userRepositoryPort;

    public UserService(UserRepositoryPort userRepositoryPort){
        this.userRepositoryPort = userRepositoryPort;
    }

    //RegisterUserUseCase

    @Override
    public User register (String name , String email, String password){
        //No permitira email duplicados
        if (userRepositoryPort.existsByEmail(email)){
            throw new IllegalArgumentException("Ya existe un usuario con el email: " + email);
        }
        User newUser = new User();
        newUser.setName(name);
        newUser.setEmail(email);
        newUser.setPassword(password);
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
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con id: " + userId));

        switch (newStatus){
            case ACTIVE -> user.activate();
            case INACTIVE -> user.deactivate();
            case SUSPENDED -> user.suspended();
            default -> throw new IllegalArgumentException("Estado no valido: " + newStatus);
        }
        return userRepositoryPort.save(user);
    }

    //GetUserUseCase
    @Override
    public User getById(Long id){
        return userRepositoryPort.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("Usuario no encontrado con id: " + id));
    }

    @Override
    public User getByEmail(String email){
        return userRepositoryPort.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Usuaeio no encontrado con email: " + email));
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
