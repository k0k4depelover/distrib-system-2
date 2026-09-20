package com.auth.statefull.cookies_auth.Config.Exceptions;

public class IllegalLoginException extends RuntimeException{
    public IllegalLoginException (String message){
        super(message);
    }
}
