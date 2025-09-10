package com.biehcey.eventcart.eventcart.product.exception;

public class ProductAlreadyExistException extends RuntimeException {
    public ProductAlreadyExistException(String name) {
        super("Product already exists: " + name);
    }
}
