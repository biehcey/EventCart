package com.biehcey.eventcart.eventcart.product.controller;

import com.biehcey.eventcart.eventcart.product.dto.NewProductDto;
import com.biehcey.eventcart.eventcart.product.dto.ProductResponseDto;
import com.biehcey.eventcart.eventcart.product.dto.UpdateProductDto;
import com.biehcey.eventcart.eventcart.product.entity.Product;
import com.biehcey.eventcart.eventcart.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponseDto> createProduct(@Valid @RequestBody NewProductDto dto){
        ProductResponseDto response = productService.createProduct(dto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable Long id){
        ProductResponseDto response = productService.getProductById(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDto> updateProductById(@PathVariable Long id,
                                                                @Valid @RequestBody UpdateProductDto dto){
        ProductResponseDto response = productService.updateProductById(id, dto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductById(@PathVariable Long id){
        productService.deleteProductById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> getAllProducts(){
        List<ProductResponseDto> response = productService.getAllProducts();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<ProductResponseDto>> findByCategory(@PathVariable String category){
        List<ProductResponseDto> response = productService.findByCategoryName(category);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
