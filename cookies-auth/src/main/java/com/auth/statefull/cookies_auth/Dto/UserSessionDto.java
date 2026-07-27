package com.auth.statefull.cookies_auth.Dto;

public class UserSessionDto {
    private Long Id;
    private String username;
    private String Roles;
    public Long getId() {
        return Id;
    }
    public void setId(Long id) {
        Id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getRoles() {
        return Roles;
    }
    public void setRoles(String roles) {
        Roles = roles;
    }
    public UserSessionDto(Long id, String username, String roles) {
        Id = id;
        this.username = username;
        Roles = roles;
    }
}
