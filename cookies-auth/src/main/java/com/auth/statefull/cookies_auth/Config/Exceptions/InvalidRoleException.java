package com.auth.statefull.cookies_auth.Config.Exceptions;

public class InvalidRoleException extends RuntimeException{
    public InvalidRoleException(String message){
        super(message) ;
    }
}
