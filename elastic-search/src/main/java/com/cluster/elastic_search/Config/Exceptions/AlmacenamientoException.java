package com.cluster.elastic_search.Config.Exceptions;

public class AlmacenamientoException extends RuntimeException{
    public AlmacenamientoException(String message, Throwable causa){
        super(message, causa);
    }
}
