package com.springproject.productservice.controllers;
import com.springproject.productservice.Exception.CrudOperationException;
import com.springproject.productservice.Exception.ProductNotExistException;
import com.springproject.productservice.Exception.ProductNotSavedException;
import com.springproject.productservice.Exception.TokenAuthenticationFailedException;
import com.springproject.productservice.Service.ProductService;

import com.springproject.productservice.commons.AuthenticationCommons;
import com.springproject.productservice.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")

public class ProductController {
    ProductService productService;
    AuthenticationCommons authenticationCommons;
    @Autowired
    public ProductController(@Qualifier("ProductServiceDatabaseIntegrated") ProductService productService,
                             AuthenticationCommons authenticationCommons){
        this.productService = productService;
        this.authenticationCommons = authenticationCommons;
    }
    @GetMapping
    public ResponseEntity<List<Product>> getAllProduct() throws ProductNotExistException {
        List<Product> products = productService.getAllProduct();
        return new ResponseEntity<>(products, HttpStatus.OK);

    }
    @GetMapping("/{id}")
    public ResponseEntity<Product> getSingleProduct(@PathVariable("id") Long id) throws ProductNotExistException {
        return new ResponseEntity<>(productService.getSingleProduct(id), HttpStatus.OK);
    }
    @PostMapping()
    public ResponseEntity<Product> addNewProduct(@RequestBody Product product) throws CrudOperationException {
        return new ResponseEntity<>(productService.addNewProduct(product), HttpStatus.OK);
    }
    @PatchMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable("id") Long id, @RequestBody Product product) throws CrudOperationException, ProductNotExistException, ProductNotSavedException {
        return new ResponseEntity<>(productService.updateProduct(id, product), HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Product> replaceProduct(@PathVariable("id") Long id, @RequestBody Product product) throws CrudOperationException, ProductNotSavedException, ProductNotExistException {
        Product returnedProduct = productService.replaceProduct(id, product);
        return new ResponseEntity<>(returnedProduct, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable("id") Long id) throws ProductNotExistException {
        return new ResponseEntity<>(productService.deleteProduct(id), HttpStatus.OK);
    }
//    the below method allow Controller level Exception handling!
//    @ExceptionHandler(ProductNotExistException.class)
//    public ResponseEntity<ExceptionDto> handleProductNotExistException(ProductNotExistException
//                                                                                   productNotExistException){
//        ExceptionDto exceptionDto = new ExceptionDto();
//        exceptionDto.setMessage(productNotExistException.getMessage() + ". This is Class " +
//                                                                            "level generated Exception Message");
//        return new ResponseEntity<>(exceptionDto, HttpStatus.NOT_FOUND);

//    }


}
