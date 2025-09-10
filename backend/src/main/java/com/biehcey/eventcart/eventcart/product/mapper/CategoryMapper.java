package com.biehcey.eventcart.eventcart.product.mapper;

import com.biehcey.eventcart.eventcart.product.dto.CategoryResponseDto;
import com.biehcey.eventcart.eventcart.product.dto.NewCategoryDto;
import com.biehcey.eventcart.eventcart.product.entity.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public Category toEntity(NewCategoryDto dto){
        Category category = new Category();
        category.setName(dto.getName());
        category.setDescription(dto.getDescription());
        return category;
    }

    public CategoryResponseDto toDto(Category category){
        CategoryResponseDto dto = new CategoryResponseDto();
        dto.setName(category.getName());
        dto.setDescription(dto.getDescription());
        dto.setId(category.getId());
        return dto;
    }
}
