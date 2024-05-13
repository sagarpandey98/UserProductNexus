package com.springproject.productservice.Exception;

public class CategoryNotExistException extends Exception{
    public CategoryNotExistException(String message){
        super(message);
    }
}
