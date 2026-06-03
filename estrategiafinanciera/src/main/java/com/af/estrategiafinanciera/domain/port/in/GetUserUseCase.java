package com.af.estrategiafinanciera.domain.port.in;

import com.af.estrategiafinanciera.domain.model.User;

import java.util.List;

public interface GetUserUseCase {
    User getByid(Long id);
    User getByEmail(String email);
    List<User> getAll();
    List<User> getAllByRole(String role);
}
