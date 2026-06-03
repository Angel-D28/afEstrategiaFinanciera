package com.af.estrategiafinanciera.infrastructure.adapter.out.persistence;


import com.af.estrategiafinanciera.domain.model.Role;
import com.af.estrategiafinanciera.domain.model.User;
import com.af.estrategiafinanciera.domain.port.out.UserRepositoryPort;
import com.af.estrategiafinanciera.infrastructure.adapter.out.persistence.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserRepositoryAdapter implements UserRepositoryPort {

    private final UserJpaRepository jpaRepository;
    private final UserMapper userMapper;

    @Override
    public User save(User user){
        var entity = userMapper.toEntity(user);
        var saved = jpaRepository.save(entity);
        return userMapper.toDomain(saved);
    }

    @Override
    public Optional<User> findById(Long id){
        return jpaRepository.findById(id)
                .map(userMapper::toDomain);
    }

    @Override
    public Optional<User> findByEmail(String email){
        return jpaRepository.findByEmail(email)
                .map(userMapper::toDomain);
    }

    @Override
    public List<User> findAll(){
        return jpaRepository.findAll()
                .stream()
                .map(userMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<User> findAllByRole(Role role){
        return jpaRepository.findAllByRole(role)
                .stream()
                .map(userMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsByEmail(String email){
        return jpaRepository.existsByEmail(email);
    }

    @Override
    public void deleteById(Long id){
        jpaRepository.deleteById(id);
    }
}
