package com.springproject.productservice.Exception;

public class TokenAuthenticationFailedException extends Exception{
    public TokenAuthenticationFailedException(String message){
        super(message);
    }
}
