package com.biehcey.eventcart.eventcart.product.service;

import com.biehcey.eventcart.eventcart.authentication.entity.SecureUser;
import com.biehcey.eventcart.eventcart.authentication.entity.User;
import com.biehcey.eventcart.eventcart.product.dto.NewProductDto;
import com.biehcey.eventcart.eventcart.product.dto.ProductResponseDto;
import com.biehcey.eventcart.eventcart.product.dto.UpdateProductDto;
import com.biehcey.eventcart.eventcart.product.entity.Category;
import com.biehcey.eventcart.eventcart.product.entity.Product;
import com.biehcey.eventcart.eventcart.product.exception.CategoryNotFoundException;
import com.biehcey.eventcart.eventcart.product.exception.ProductAlreadyExistException;
import com.biehcey.eventcart.eventcart.product.exception.ProductNotFoundException;
import com.biehcey.eventcart.eventcart.product.mapper.ProductMapper;
import com.biehcey.eventcart.eventcart.product.repository.CategoryRepository;
import com.biehcey.eventcart.eventcart.product.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    @Transactional
    @PreAuthorize("hasRole('ADMIN') or hasRole('SELLER')")
    public ProductResponseDto createProduct(NewProductDto dto){
        validateProductName(dto.getName());

        User currentUser = getCurrentUser();
        Category category = findCategory(dto.getCategoryName());

        Product product = productMapper.toEntity(dto, category);
        product.setOwner(currentUser);

        Product saved = productRepository.save(product);
        return productMapper.toDto(saved);
    }

    public ProductResponseDto getProductById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() ->
                new ProductNotFoundException("Product not found by id: "+id));
        return productMapper.toDto(product);
    }

    private Product findProductById(Long id){
        return productRepository.findById(id).orElseThrow(() ->
                new ProductNotFoundException("Product not found by id: "+id));
    }

    @PreAuthorize("hasRole('ADMIN') or (hasRole('SELLER') and @productSecurity.isOwner(#id, authentication))")
    @Transactional
    public ProductResponseDto updateProductById(Long id, UpdateProductDto dto){
        Product product = findProductById(id);
        productMapper.updateEntity(dto, product);
        Product saved = productRepository.save(product);
        return productMapper.toDto(saved);
    }


    @PreAuthorize("hasRole('ADMIN') or (hasRole('SELLER') and @productSecurity.isOwner(#id, authentication))")
    @Transactional
    public void  deleteProductById(Long id){
        Product product = findProductById(id);
        productRepository.delete(product);
    }


    public List<ProductResponseDto> getAllProducts(){
        return productRepository.findAll().stream()
                .map(productMapper::toDto).toList();
    }


    public List<ProductResponseDto> findByCategoryName(String categoryName){
        return productRepository.findByCategory_NameIgnoreCase(categoryName)
                        .stream().map(productMapper::toDto).toList();
    }

    public void validateProductName(String name){
        productRepository.findByNameIgnoreCase(name)
                .ifPresent(p -> {
                    throw new ProductAlreadyExistException(name);}
                );
    }

    private User getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SecureUser secureUser = (SecureUser) authentication.getPrincipal();
        return secureUser.getUser();
    }

    private Category findCategory(String categoryName){
        return categoryRepository.findByNameIgnoreCase(categoryName)
                .orElseThrow(() -> new CategoryNotFoundException(categoryName));
    }

}
