package com.springproject.productservice.Service;

import com.springproject.productservice.Exception.CategoryNotExistException;
import com.springproject.productservice.Exception.CrudOperationException;
import com.springproject.productservice.Exception.ProductNotExistException;
import com.springproject.productservice.dtos.FakeStoreProductDto;
import com.springproject.productservice.models.Category;
import com.springproject.productservice.models.Product;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpMessageConverterExtractor;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@Service("FakeStoreProductService")
public class FakeStoreProductService implements ProductService{
    private RestTemplate restTemplate;
    @Autowired
    public FakeStoreProductService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    private Product convertFakeStoreProductToProduct(FakeStoreProductDto fakeStoreProduct){
        Product product = new Product();
        product.setId(fakeStoreProduct.getId());
        product.setTitle(fakeStoreProduct.getTitle());
        product.setDescription(fakeStoreProduct.getDescription());
        product.setPrice(fakeStoreProduct.getPrice());
        product.setImage(fakeStoreProduct.getImage());
        product.setCategory(new Category());
        product.getCategory().setName(fakeStoreProduct.getCategory());

        return product;
    }

    private FakeStoreProductDto convertProductToFakeStoreProduct(Product product) {
        FakeStoreProductDto fakeStoreProductDto = new FakeStoreProductDto();

        if (product.getId() != null) {
            fakeStoreProductDto.setId(product.getId());
        }
        if (product.getTitle() != null) {
            fakeStoreProductDto.setTitle(product.getTitle());
        }
        if (product.getDescription() != null) {
            fakeStoreProductDto.setDescription(product.getDescription());
        }
        if (product.getPrice() != 0) {
            fakeStoreProductDto.setPrice(product.getPrice());
        }
        if (product.getImage() != null) {
            fakeStoreProductDto.setImage(product.getImage());
        }
        if (product.getCategory() != null && product.getCategory().getName() != null) {
            fakeStoreProductDto.setCategory(product.getCategory().getName());
        }

        return fakeStoreProductDto;
    }

    @Override
    public List<Product> getAllProduct() throws ProductNotExistException {
        FakeStoreProductDto[] fakeStoreProductsArray = restTemplate.getForObject("https://fakestoreapi.com/products",
                                                                                        FakeStoreProductDto[].class);
        if (fakeStoreProductsArray==null){
            throw new ProductNotExistException("No Products available to display");
        }
        List<Product> products = new ArrayList<>();
        for(FakeStoreProductDto fakeStoreProducts: fakeStoreProductsArray){
            products.add(convertFakeStoreProductToProduct(fakeStoreProducts));
        }
        return products;
    }

    @Override
    public Product getSingleProduct(Long id) throws ProductNotExistException {
        FakeStoreProductDto fakeStoreProduct = restTemplate.getForObject("https://fakestoreapi.com/products/" + id,
                                                                            FakeStoreProductDto.class);
        if(fakeStoreProduct==null){
            throw new ProductNotExistException("Product with id: " + id + " does not exists");
        }
        return convertFakeStoreProductToProduct(fakeStoreProduct);

    }

    @Override
    public List<String> getAllCategories() throws CategoryNotExistException {
        String[] categoriesArray = restTemplate.getForObject("https://fakestoreapi.com/products/categories",
                                        String[].class);

        if(categoriesArray == null){
            throw new CategoryNotExistException("No category to display");
        }
        return List.of(categoriesArray);
    }

    @Override
    public List<Product> getInCategory(String category) throws CategoryNotExistException {
        List<Product> productList = new ArrayList<>();
        FakeStoreProductDto[] productArray = restTemplate.getForObject("https://fakestoreapi.com/products/category/"
                                                                        + category, FakeStoreProductDto[].class );
        if(productArray==null){
            throw new CategoryNotExistException("Category with name "+ category + " not found");
        }
        for(FakeStoreProductDto fakeStoreProductDto: productArray){
               productList.add(convertFakeStoreProductToProduct(fakeStoreProductDto));
        }
        return productList;
    }

    @Override
    public Product addNewProduct(Product product) throws CrudOperationException {
        FakeStoreProductDto fakeStoreProductDto = convertProductToFakeStoreProduct(product);
        RequestCallback requestCallback = restTemplate.httpEntityCallback(fakeStoreProductDto,
                FakeStoreProductDto.class);
        HttpMessageConverterExtractor<FakeStoreProductDto> responseExtractor =
                new HttpMessageConverterExtractor<>(FakeStoreProductDto.class, restTemplate.getMessageConverters());
        FakeStoreProductDto response =  restTemplate.execute("https://fakestoreapi.com/products",
                HttpMethod.PATCH, requestCallback, responseExtractor);
        if(response==null){
            throw new CrudOperationException("operation to post the product into database has failed");
        }
        return convertFakeStoreProductToProduct(response);

    }

    @Override
    public Product updateProduct(Long id, Product product) throws CrudOperationException {
        FakeStoreProductDto fakeStoreProductDto = convertProductToFakeStoreProduct(product);
        fakeStoreProductDto.setId(id);

        RequestCallback requestCallback = restTemplate.httpEntityCallback(fakeStoreProductDto,
                                                                            FakeStoreProductDto.class);
        HttpMessageConverterExtractor<FakeStoreProductDto> responseExtractor =
                new HttpMessageConverterExtractor<>(FakeStoreProductDto.class, restTemplate.getMessageConverters());
        FakeStoreProductDto response =  restTemplate.execute("https://fakestoreapi.com/products/" + id,
                                                                HttpMethod.PATCH, requestCallback, responseExtractor);
        if(response == null){
            throw new CrudOperationException("operation to update the product has failed");
        }
        return convertFakeStoreProductToProduct(response);

    }

    @Override
    public Product replaceProduct(Long id, Product product) throws CrudOperationException {
        FakeStoreProductDto fakeStoreProductDto = new FakeStoreProductDto();
            fakeStoreProductDto.setId(id);
            fakeStoreProductDto.setTitle(product.getTitle());
            fakeStoreProductDto.setDescription(product.getDescription());
            fakeStoreProductDto.setPrice(product.getPrice());
            fakeStoreProductDto.setImage(product.getImage());
            fakeStoreProductDto.setCategory(product.getCategory().getName());
        RequestCallback requestCallback = restTemplate.httpEntityCallback(fakeStoreProductDto,
                                                                            FakeStoreProductDto.class);
        HttpMessageConverterExtractor<FakeStoreProductDto> responseExtractor =
                new HttpMessageConverterExtractor<>(FakeStoreProductDto.class, restTemplate.getMessageConverters());
        FakeStoreProductDto response = restTemplate.execute("https://fakestoreapi.com/products/" + id,
                                                                HttpMethod.PUT, requestCallback, responseExtractor);
        if(response == null){
            throw new CrudOperationException("operation to replace the product has failed");
        }
        return convertFakeStoreProductToProduct(response);
    }

    @Override
    public Product deleteProduct(Long id) throws ProductNotExistException {
        RequestCallback requestCallback = restTemplate.httpEntityCallback(new FakeStoreProductDto(),
                                                                            FakeStoreProductDto.class);
        HttpMessageConverterExtractor<FakeStoreProductDto> responseExtractor =
                new HttpMessageConverterExtractor<>(FakeStoreProductDto.class, restTemplate.getMessageConverters());
        FakeStoreProductDto response = restTemplate.execute("https://fakestoreapi.com/products/" + id,
                                                            HttpMethod.DELETE, requestCallback, responseExtractor);
        if(response == null){
            throw new ProductNotExistException("operation to delete the product has failed");
        }
        return convertFakeStoreProductToProduct(response);
    }
}
