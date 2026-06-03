package com.af.estrategiafinanciera.domain.port.in;

import com.af.estrategiafinanciera.domain.model.User;
import com.af.estrategiafinanciera.domain.model.UserStatus;

public interface UpdateUserStatusUseCase {
    User updateStatus(Long id, UserStatus newStatus);
}
