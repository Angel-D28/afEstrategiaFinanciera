package com.af.estrategiafinanciera.domain.port.in;

import com.af.estrategiafinanciera.domain.model.Role;
import com.af.estrategiafinanciera.domain.model.User;

public interface UpdateUserRoleUseCase {
    User updateRole (Long userId, Role newRole);
}
