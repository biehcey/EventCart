package com.biehcey.eventcart.eventcart.product.helper;

import com.biehcey.eventcart.eventcart.product.exception.CategoryNotFoundException;
import com.biehcey.eventcart.eventcart.product.exception.ProductAlreadyExistException;
import com.biehcey.eventcart.eventcart.product.exception.ProductNotFoundException;
import com.biehcey.eventcart.eventcart.util.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;


@RestControllerAdvice
public class ProductExceptionHandler {

    @ExceptionHandler(value = {ProductAlreadyExistException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage productAlreadyExistException(ProductAlreadyExistException exception, WebRequest request){
        return new ErrorMessage(
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now(),
                exception.getMessage(),
                request.getDescription(false)
        );
    }

    @ExceptionHandler(value = {ProductNotFoundException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorMessage productNotFoundException(ProductNotFoundException exception, WebRequest request){
        return new ErrorMessage(
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now(),
                exception.getMessage(),
                request.getDescription(false)
        );
    }

    @ExceptionHandler(value = {CategoryNotFoundException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorMessage categoryNotFoundException(CategoryNotFoundException exception, WebRequest request){
        return new ErrorMessage(
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now(),
                exception.getMessage(),
                request.getDescription(false)
        );
    }

    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessage handleAllExceptions(Exception exception, WebRequest request){
        return new ErrorMessage(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                LocalDateTime.now(),
                exception.getMessage(),
                request.getDescription(false)
        );
    }
}
