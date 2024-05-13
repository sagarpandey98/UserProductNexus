package com.springproject.productservice.controllers;
import com.springproject.productservice.Exception.CategoryNotExistException;
import com.springproject.productservice.Service.ProductService;
import com.springproject.productservice.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products/category")

public class CategoryController {
    ProductService productService;
    @Autowired
    public CategoryController(@Qualifier("ProductServiceDatabaseIntegrated") ProductService productService){
        this.productService = productService;
    }
    @GetMapping
    public List<String> getAllCategory() throws CategoryNotExistException {
        return productService.getAllCategories();
    }
    @GetMapping("/{category}")
    public ResponseEntity<List<Product>> getInCategory(@PathVariable("category") String category) throws CategoryNotExistException {
        List<Product> products = productService.getInCategory(category);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
}
