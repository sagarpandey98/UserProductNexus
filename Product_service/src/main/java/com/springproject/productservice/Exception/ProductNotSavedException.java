package com.springproject.productservice.Exception;

public class ProductNotSavedException extends Exception{
    public ProductNotSavedException(String message){
        super(message);
    }
}
