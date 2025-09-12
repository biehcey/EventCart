package com.biehcey.eventcart.eventcart.product.mapper;

import com.biehcey.eventcart.eventcart.product.dto.NewProductDto;
import com.biehcey.eventcart.eventcart.product.dto.ProductResponseDto;
import com.biehcey.eventcart.eventcart.product.dto.UpdateProductDto;
import com.biehcey.eventcart.eventcart.product.entity.Category;
import com.biehcey.eventcart.eventcart.product.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(target = "name", source = "dto.name")
    @Mapping(target = "category", source = "category")
    @Mapping(target = "description", source = "dto.description")
    @Mapping(target = "id", ignore = true)
    Product toEntity(NewProductDto dto, Category category);

    @Mapping(target = "categoryName", source = "category.name")
    @Mapping(target = "owner", source = "owner.username")
    ProductResponseDto toDto(Product product);


    void updateEntity(UpdateProductDto dto, @MappingTarget Product product);

}
