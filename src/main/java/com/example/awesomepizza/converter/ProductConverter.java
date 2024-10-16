package com.example.awesomepizza.converter;

import com.example.awesomepizza.dto.ProductDTO;
import com.example.awesomepizza.entity.Product;

import java.util.List;
import java.util.stream.Collectors;

public class ProductConverter {
    public static Product convertAsEntity(ProductDTO from) {
        Product to = new Product();
        to.setName(from.getName());
        to.setDescription(from.getDescription());
        to.setPrice(from.getPrice());
        to.setUuid(from.getUuid());
        return to;
    }


    public static ProductDTO convertAsDTO(Product from) {
        ProductDTO to = new ProductDTO();
        to.setName(from.getName());
        to.setUuid(from.getUuid());
        to.setDescription(from.getDescription());
        to.setPrice(from.getPrice());
        to.setCreatedAt(from.getCreatedAt());
        to.setUpdatedAt(from.getUpdatedAt());
        return to;
    }


    public static List<ProductDTO> convertAsDTO(List<Product> from) {
        return from.stream().map(ProductConverter::convertAsDTO).collect(Collectors.toList());
    }

    public static List<Product> convertAsEntity(List<ProductDTO> from) {
        return from.stream().map(ProductConverter::convertAsEntity).collect(Collectors.toList());
    }

}
