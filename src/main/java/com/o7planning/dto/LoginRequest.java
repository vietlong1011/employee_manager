package com.o7planning.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;

    private String password;
}
