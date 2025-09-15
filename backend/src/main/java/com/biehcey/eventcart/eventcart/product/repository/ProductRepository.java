package com.biehcey.eventcart.eventcart.product.repository;

import com.biehcey.eventcart.eventcart.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategory_Id(Long categoryId);
    Page<Product> findByPriceBetween(BigDecimal min, BigDecimal max, Pageable pageable);
    Optional<Product> findByNameIgnoreCase(String name);
    Page<Product> findByCategory_NameIgnoreCase(String name, Pageable pageable);

}
