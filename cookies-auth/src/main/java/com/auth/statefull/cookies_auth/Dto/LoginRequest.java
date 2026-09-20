package com.auth.statefull.cookies_auth.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class LoginRequest {
    @NotBlank(message = "Debe ingresar un username")
    private String username;
    @NotBlank (message = "Debe ingresar contraseña")
    @Size (min=8, message = "La contraseña debe tener 8 caracteres")
    private String password;

    
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    
}
