package com.springproject.productservice.Service;
import com.springproject.productservice.Exception.CategoryNotExistException;
import com.springproject.productservice.Exception.CrudOperationException;
import com.springproject.productservice.Exception.ProductNotExistException;
import com.springproject.productservice.Exception.ProductNotSavedException;
import com.springproject.productservice.models.Product;

import java.util.List;
public interface ProductService {
    public List<Product> getAllProduct() throws ProductNotExistException;
    public Product getSingleProduct(Long id) throws ProductNotExistException;
    public List<String> getAllCategories() throws CategoryNotExistException;
    public List<Product> getInCategory(String category) throws CategoryNotExistException;
    public Product addNewProduct(Product product) throws CrudOperationException;
    public Product updateProduct(Long id,Product product) throws CrudOperationException, ProductNotSavedException, ProductNotExistException;
    public Product replaceProduct(Long id,Product product) throws CrudOperationException, ProductNotSavedException, ProductNotExistException;
    public Product deleteProduct(Long id) throws ProductNotExistException;
}
