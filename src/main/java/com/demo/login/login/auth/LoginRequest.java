package com.demo.login.login.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder //contruye objetos
@AllArgsConstructor
@NoArgsConstructor

public class LoginRequest {
    String username;
    String password;
}
