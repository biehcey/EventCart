package com.biehcey.eventcart.eventcart.product.mapper;

import com.biehcey.eventcart.eventcart.product.dto.CategoryResponseDto;
import com.biehcey.eventcart.eventcart.product.dto.NewCategoryDto;
import com.biehcey.eventcart.eventcart.product.entity.Category;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    Category toEntity(NewCategoryDto dto);
    CategoryResponseDto toDto(Category category);
}
