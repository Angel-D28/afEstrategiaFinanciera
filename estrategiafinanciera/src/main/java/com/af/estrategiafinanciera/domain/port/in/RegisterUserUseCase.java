package com.af.estrategiafinanciera.domain.port.in;

import com.af.estrategiafinanciera.domain.model.User;

public interface RegisterUserUseCase {
    User register(String name , String email, String password);
}
