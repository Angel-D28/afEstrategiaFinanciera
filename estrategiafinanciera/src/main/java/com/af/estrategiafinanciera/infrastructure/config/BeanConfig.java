package com.af.estrategiafinanciera.infrastructure.config;


import com.af.estrategiafinanciera.domain.port.out.PasswordEncoderPort;
import com.af.estrategiafinanciera.domain.port.out.PlanRepositoryPort;
import com.af.estrategiafinanciera.domain.port.out.UserRepositoryPort;
import com.af.estrategiafinanciera.domain.service.PlanService;
import com.af.estrategiafinanciera.domain.service.UserService;
import com.af.estrategiafinanciera.infrastructure.security.AuthService;
import com.af.estrategiafinanciera.infrastructure.security.JwtService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
public class BeanConfig {

    @Bean
    public UserService userService(UserRepositoryPort userRepositoryPort,
                                   PasswordEncoderPort passwordEncoderPort){
        return new UserService(userRepositoryPort, passwordEncoderPort);
    }
    @Bean
    public PlanService planService(PlanRepositoryPort planRepositoryPort){
        return new PlanService(planRepositoryPort);
    }
    @Bean
    public AuthService authService(UserRepositoryPort userRepositoryPort,
                                   JwtService jwtService,
                                   AuthenticationManager authenticationManager,
                                   UserDetailsService userDetailsService) {
        return new AuthService(
                userRepositoryPort,
                jwtService,
                authenticationManager,
                userDetailsService
        );
    }
}
