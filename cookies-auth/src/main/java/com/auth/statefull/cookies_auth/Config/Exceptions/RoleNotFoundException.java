package com.auth.statefull.cookies_auth.Config.Exceptions;

public class RoleNotFoundException extends RuntimeException{
    public  RoleNotFoundException(String message){
        super(message);
    }
}
