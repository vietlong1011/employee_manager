package com.o7planning.dto;

import com.o7planning.entity.user.Role;
import lombok.Data;

import java.util.Set;

@Data
public class LoginRequest {
    private String username;

    private String password;

    private Set<Role> role;
}
