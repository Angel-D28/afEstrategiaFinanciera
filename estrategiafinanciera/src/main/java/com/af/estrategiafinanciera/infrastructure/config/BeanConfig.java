package com.af.estrategiafinanciera.infrastructure.config;


import com.af.estrategiafinanciera.domain.port.out.*;
import com.af.estrategiafinanciera.domain.service.PaymentService;
import com.af.estrategiafinanciera.domain.service.PlanService;
import com.af.estrategiafinanciera.domain.service.SubscriptionService;
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
    public SubscriptionService subscriptionService(
            SubscriptionRepositoryPort subscriptionRepositoryPort,
            UserRepositoryPort userRepositoryPort,
            PlanRepositoryPort planRepositoryPort) {
        return new SubscriptionService(
                subscriptionRepositoryPort,
                userRepositoryPort,
                planRepositoryPort);
    }

    @Bean
    public PaymentService paymentService(
            PaymentRepositoryPort paymentRepositoryPort,
            SubscriptionRepositoryPort subscriptionRepositoryPort
    ){
        return new PaymentService(
                paymentRepositoryPort,
                subscriptionRepositoryPort
        );
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
