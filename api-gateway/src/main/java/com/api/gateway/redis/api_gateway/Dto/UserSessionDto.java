package com.api.gateway.redis.api_gateway.Dto;

public class UserSessionDto {
    private Integer Id;

    private String Username;

    private String Roles;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getRoles() {
        return Roles;
    }

    public void setRoles(String roles) {
        Roles = roles;
    }


    
}
