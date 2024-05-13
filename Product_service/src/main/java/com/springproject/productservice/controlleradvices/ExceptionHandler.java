package com.springproject.productservice.controlleradvices;

import com.springproject.productservice.Exception.ProductNotExistException;
import com.springproject.productservice.Exception.ProductNotSavedException;
import com.springproject.productservice.Exception.TokenAuthenticationFailedException;
import com.springproject.productservice.dtos.ArithmeticExceptionDTO;
import com.springproject.productservice.dtos.ExceptionDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice // within this class there are some methods that may modify what controller is returning
//Exception handler can be of two levels one is global and another is class level.
public class ExceptionHandler {
     @org.springframework.web.bind.annotation.ExceptionHandler(ArithmeticException.class)
    public ResponseEntity<ArithmeticExceptionDTO> handleArithmeticException(){
        ArithmeticExceptionDTO arithmeticExceptionDTO = new ArithmeticExceptionDTO();
        arithmeticExceptionDTO.setMessage("something went wrong");
        return new ResponseEntity<>(arithmeticExceptionDTO, HttpStatus.OK);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(ProductNotExistException.class)
    public ResponseEntity<ExceptionDto> handleProductNotExistException(ProductNotExistException exception){
        ExceptionDto exceptionDto = new ExceptionDto();
        exceptionDto.setMessage(exception.getMessage());
        return new ResponseEntity<>(exceptionDto, HttpStatus.OK);
    }
    @org.springframework.web.bind.annotation.ExceptionHandler(TokenAuthenticationFailedException.class)
    public ResponseEntity<ExceptionDto> handleTokenAuthenticationFailedException
            (TokenAuthenticationFailedException exception){
         ExceptionDto exceptionDto = new ExceptionDto();
         exceptionDto.setMessage(exception.getMessage());
         return new ResponseEntity<>(exceptionDto, HttpStatus.UNAUTHORIZED);
    }
}
