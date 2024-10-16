package com.example.awesomepizza.dao;

import com.example.awesomepizza.entity.Product;
import com.example.awesomepizza.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class ProductDAO {

    private ProductRepository productRepository;

    @Autowired
    public ProductDAO(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product save(Product product) {
        return this.productRepository.save(product);
    }

    public Optional<Product> getById(UUID uuid) {
        return this.productRepository.findById(uuid);
    }

    public List<Product> getAll() {
        return this.productRepository.findAll();
    }
}
