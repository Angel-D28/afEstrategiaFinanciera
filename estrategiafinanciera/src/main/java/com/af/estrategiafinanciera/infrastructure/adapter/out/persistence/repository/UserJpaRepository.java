package com.af.estrategiafinanciera.infrastructure.adapter.out.persistence.repository;


import com.af.estrategiafinanciera.domain.model.Role;
import com.af.estrategiafinanciera.infrastructure.adapter.out.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserJpaRepository extends JpaRepository<UserEntity , Long> {
    Optional<UserEntity> findByEmail(String email);
    List<UserEntity> findAllByRole(Role role);
    boolean existsByEmail(String email);
}
