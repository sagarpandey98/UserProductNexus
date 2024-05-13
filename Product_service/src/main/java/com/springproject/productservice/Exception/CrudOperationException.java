package com.springproject.productservice.Exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CrudOperationException extends Exception{
    String description;
    String Sqlerror;
    public CrudOperationException(String message){
        super(message);
        this.description = message;
    }

}
