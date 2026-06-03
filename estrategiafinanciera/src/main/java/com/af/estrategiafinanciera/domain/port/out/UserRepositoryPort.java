package com.af.estrategiafinanciera.domain.port.out;

import com.af.estrategiafinanciera.domain.model.Role;
import com.af.estrategiafinanciera.domain.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepositoryPort {
    User save(User user);
    Optional<User> findById(Long id);
    Optional<User> findByEmail(String email);
    List<User> findAll();
    List<User> findAllByRole(Role role);
    boolean existByEmail(String email);
    void deleteById(Long id);
}
