package com.example.awesomepizza.service;

import com.example.awesomepizza.converter.ProductConverter;
import com.example.awesomepizza.dao.ProductDAO;
import com.example.awesomepizza.dto.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Component
public class ProductService {

    private ProductDAO productDao;

    @Autowired
    public ProductService(ProductDAO productDao) {
        this.productDao = productDao;
    }

    public ProductDTO create(ProductDTO productDTO) {
        return ProductConverter.convertAsDTO(this.productDao.save(ProductConverter.convertAsEntity(productDTO)));
    }

    public List<ProductDTO> get() {
        return ProductConverter.convertAsDTO(this.productDao.getAll());
    }

    public ProductDTO get(UUID uuid) {
        return ProductConverter.convertAsDTO(this.productDao.getById(uuid).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }

}
