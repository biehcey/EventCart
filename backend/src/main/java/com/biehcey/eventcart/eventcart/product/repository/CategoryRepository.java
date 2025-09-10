package com.biehcey.eventcart.eventcart.product.repository;

import com.biehcey.eventcart.eventcart.product.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByNameIgnoreCase(String name);
}
