package com.auth.statefull.cookies_auth.Dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public class RegisterRequest {
    @NotBlank(message = "El username es obligatorio")
    private String username;
    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min=8, message = "La contraseña debe tener al menos 8 caracteres")
    private String password;
    @NotBlank(message = "Debe existir al menos un rol en la solicitud")
    private List<String> Roles;
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
    public List<String> getRoles() {
        return Roles;
    }
    public void setRoles(List<String> roles) {
        Roles = roles;
    }

    
    

}
