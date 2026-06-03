package com.af.estrategiafinanciera.infrastructure.config;


import com.af.estrategiafinanciera.domain.port.out.UserRepositoryPort;
import com.af.estrategiafinanciera.domain.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public UserService userService(UserRepositoryPort userRepositoryPort){
        return new UserService(userRepositoryPort);
    }
}
