package com.biehcey.eventcart.eventcart.product.service;

import com.biehcey.eventcart.eventcart.product.dto.CategoryResponseDto;
import com.biehcey.eventcart.eventcart.product.dto.NewCategoryDto;
import com.biehcey.eventcart.eventcart.product.entity.Category;
import com.biehcey.eventcart.eventcart.product.exception.CategoryAlreadyExistException;
import com.biehcey.eventcart.eventcart.product.mapper.CategoryMapper;
import com.biehcey.eventcart.eventcart.product.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public CategoryResponseDto createCategory(NewCategoryDto dto){
        isCategoryAlreadyPresent(dto.getName());
        Category category = categoryMapper.toEntity(dto);
        Category saved = categoryRepository.save(category);
        return categoryMapper.toDto(saved);
    }

    public List<CategoryResponseDto> getAllCategories(){
        return categoryRepository.findAll().stream()
                .map(categoryMapper::toDto).collect(Collectors.toList());
    }

    public void isCategoryAlreadyPresent(String name){
        categoryRepository.findByNameIgnoreCase(name).ifPresent(c -> {
            throw new CategoryAlreadyExistException(name);
        });
    }
}

