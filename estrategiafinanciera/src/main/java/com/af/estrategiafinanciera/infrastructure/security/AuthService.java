package com.af.estrategiafinanciera.infrastructure.security;

import com.af.estrategiafinanciera.application.dto.AuthResponse;
import com.af.estrategiafinanciera.application.dto.LoginRequest;
import com.af.estrategiafinanciera.domain.model.User;
import com.af.estrategiafinanciera.domain.port.in.AuthUseCase;
import com.af.estrategiafinanciera.domain.port.out.UserRepositoryPort;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public class AuthService implements AuthUseCase {

    private final UserRepositoryPort userRepositoryPort;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;

    public AuthService(UserRepositoryPort userRepositoryPort,
                       JwtService jwtService,
                       AuthenticationManager authenticationManager,
                       UserDetailsService userDetailsService) {
        this.userRepositoryPort = userRepositoryPort;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public AuthResponse login(LoginRequest request){
        //verifica credenciales de spring security
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );

        UserDetails userDetails =userDetailsService
                .loadUserByUsername(request.email());

        User user = userRepositoryPort.findByEmail(request.email())
                .orElseThrow(() -> new IllegalArgumentException("Usuairo no encontrado"));

        String token = jwtService.generateToken(userDetails);

        return new AuthResponse(token, user.getName(), user.getEmail(), user.getRole());
    }
}
