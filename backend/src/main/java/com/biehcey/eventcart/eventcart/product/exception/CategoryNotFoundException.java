package com.biehcey.eventcart.eventcart.product.exception;

public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException(String name) {
        super("Category not found: " + name);
    }
}
