package com.springproject.productservice.Exception;

public class ProductNotExistException extends Exception{
    public ProductNotExistException(String message){
        super(message);
    }
}
