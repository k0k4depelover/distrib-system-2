package com.auth.statefull.cookies_auth.Dto;

import java.util.List;

import com.auth.statefull.cookies_auth.Entity.Role;

public class RegisterRequest {
    private String username;
    private String password;
    private List<Role> Roles;
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
    public List<Role> getRoles() {
        return Roles;
    }
    public void setRoles(List<Role> roles) {
        Roles = roles;
    }

    
    

}
