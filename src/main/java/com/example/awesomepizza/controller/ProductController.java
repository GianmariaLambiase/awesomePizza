package com.example.awesomepizza.controller;

import com.example.awesomepizza.dto.ProductDTO;
import com.example.awesomepizza.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
@Validated

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping()
    public ResponseEntity createOrder(@Valid @RequestBody ProductDTO productDTO) {
        return ResponseEntity.ok().body(this.productService.create(productDTO));
    }

    @GetMapping()
    public ResponseEntity<List<ProductDTO>> getAllOrders() {
        return ResponseEntity.ok().body(productService.get());
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<ProductDTO> get(@PathVariable UUID uuid) throws ChangeSetPersister.NotFoundException {
        return ResponseEntity.ok().body(productService.get(uuid));
    }

}