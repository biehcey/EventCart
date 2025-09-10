package com.biehcey.eventcart.eventcart.product.repository;

import com.biehcey.eventcart.eventcart.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategory_Id(Long categoryId);
    List<Product> findByPriceBetween(BigDecimal min, BigDecimal max);
    Optional<Product> findByNameIgnoreCase(String name);
    List<Product> findByCategory_NameIgnoreCase(String name);
    //cachelenebilir
    //List<Product> findTop5ByCategory_IdOrderByStockQuantityDesc(Long categoryId);
}
