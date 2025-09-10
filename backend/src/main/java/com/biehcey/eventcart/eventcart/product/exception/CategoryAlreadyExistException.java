package com.biehcey.eventcart.eventcart.product.exception;

public class CategoryAlreadyExistException extends RuntimeException {
    public CategoryAlreadyExistException(String name) {

        super("Category already exist: " + name);
    }
}
