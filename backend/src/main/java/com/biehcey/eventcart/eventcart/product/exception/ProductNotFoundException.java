package com.biehcey.eventcart.eventcart.product.exception;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException() {
    }
    public ProductNotFoundException(String message) {
        super(message);
    }
}
