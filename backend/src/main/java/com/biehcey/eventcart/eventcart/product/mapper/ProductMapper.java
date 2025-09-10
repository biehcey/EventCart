package com.biehcey.eventcart.eventcart.product.mapper;

import com.biehcey.eventcart.eventcart.product.dto.NewProductDto;
import com.biehcey.eventcart.eventcart.product.dto.ProductResponseDto;
import com.biehcey.eventcart.eventcart.product.dto.UpdateProductDto;
import com.biehcey.eventcart.eventcart.product.entity.Category;
import com.biehcey.eventcart.eventcart.product.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public Product toEntity(NewProductDto dto, Category category){
        Product product = new Product();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setCategory(category);
        product.setStockQuantity(dto.getStockQuantity());
        return product;
    }

    public ProductResponseDto toDto(Product product){
        ProductResponseDto dto = new ProductResponseDto();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setCategoryName(product.getCategory().getName());
        dto.setPrice(product.getPrice());
        dto.setCreatedAt(product.getCreatedAt());
        dto.setUpdatedAt(product.getCreatedAt());
        dto.setStockQuantity(product.getStockQuantity());
        dto.setOwner(product.getOwner().getUsername());
        return dto;
    }

    public void updateEntity(UpdateProductDto dto, Product product){
        product.setPrice(dto.getPrice());
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setStockQuantity(dto.getStockQuantity());
    }
}
