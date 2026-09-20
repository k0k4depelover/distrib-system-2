package com.auth.statefull.cookies_auth.Dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public class RegisterRequest {
    @NotBlank(message = "El username es obligatorio")
    private String username;
    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min=8, message = "La contraseña debe tener al menos 8 caracteres")
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
