package com.springproject.productservice.repositories;

import com.springproject.productservice.models.Product;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.util.Streamable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @NonNull
    Optional<Product> findById(Long id);
    @NonNull
    Product save(Product product);
    @NonNull
    List<Product> findAll();
    List<Product> findByCategory_Name(String category);
}
