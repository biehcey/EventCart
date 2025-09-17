package com.biehcey.eventcart.eventcart.product.service;

import com.biehcey.eventcart.eventcart.authentication.entity.SecureUser;
import com.biehcey.eventcart.eventcart.authentication.entity.User;
import com.biehcey.eventcart.eventcart.product.dto.NewProductDto;
import com.biehcey.eventcart.eventcart.product.dto.ProductResponseDto;
import com.biehcey.eventcart.eventcart.product.dto.RestPage;
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
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
@EnableCaching
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

    @Cacheable("product")
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

    @Cacheable(value = "products", key = "#pageable.pageNumber + '-' + #pageable.pageSize")
    public RestPage<ProductResponseDto> getAllProducts(Pageable pageable){
        return new RestPage<>(productRepository.findAll(pageable)
                .map(productMapper::toDto));
    }

    @Cacheable(value = "category_products", key = "#name + '-' + #pageable.pageNumber + '-' + #pageable.pageSize")
    public RestPage<ProductResponseDto> findByCategoryName(String name, Pageable pageable){
        return new RestPage<>(productRepository.findByCategory_NameIgnoreCase(name, pageable)
                        .map(productMapper::toDto));
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

    private boolean isStockLow(Product product){
        return product.getStockQuantity() <= 5;
    }

}
