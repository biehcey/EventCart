package com.biehcey.eventcart.eventcart.product.controller;

import com.biehcey.eventcart.eventcart.product.dto.CategoryResponseDto;
import com.biehcey.eventcart.eventcart.product.dto.NewCategoryDto;
import com.biehcey.eventcart.eventcart.product.entity.Category;
import com.biehcey.eventcart.eventcart.product.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping("/create")
    public Category createCategory(@RequestBody NewCategoryDto dto){
        return categoryService.createCategory(dto);
    }

    @GetMapping("/all")
    public List<CategoryResponseDto> getAllCategories(){
        return categoryService.getAllCategories();
    }
}
