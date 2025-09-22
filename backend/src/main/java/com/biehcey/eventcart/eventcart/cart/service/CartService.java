package com.biehcey.eventcart.eventcart.cart.service;

import com.biehcey.eventcart.eventcart.authentication.entity.SecureUser;
import com.biehcey.eventcart.eventcart.authentication.entity.User;
import com.biehcey.eventcart.eventcart.cart.mapper.CartMapper;
import com.biehcey.eventcart.eventcart.cart.dto.CartDto;
import com.biehcey.eventcart.eventcart.cart.entity.Cart;
import com.biehcey.eventcart.eventcart.cart.entity.CartItem;
import com.biehcey.eventcart.eventcart.cart.repository.CartItemRepository;
import com.biehcey.eventcart.eventcart.cart.repository.CartRepository;
import com.biehcey.eventcart.eventcart.product.entity.Product;
import com.biehcey.eventcart.eventcart.product.exception.ProductNotFoundException;
import com.biehcey.eventcart.eventcart.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final CartMapper cartMapper;

    public CartDto getCart(){
        return cartMapper.toDto(getOrCreateCartEntity());
    }
    private Cart getOrCreateCartEntity(){
        User currentUser = getCurrentUser();
        return cartRepository.findByUser(currentUser).orElseGet(() -> {
            Cart cart = new Cart();
            cart.setUser(currentUser);
            cart.setTotalPrice(BigDecimal.ZERO);
            return cartRepository.save(cart);
        });
    }

    public CartDto addProductToCart(Long productId, int quantity){
        Cart cart = getOrCreateCartEntity();
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id:" + productId));
        if(product.getStockQuantity() < quantity)
            throw new RuntimeException("Not sufficient quantity!!");
        CartItem cartItem = cartItemRepository.findByCartAndProduct(cart, product)
                .orElseGet(() -> {
                    CartItem newItem = new CartItem();
                    newItem.setCart(cart);
                    newItem.setProduct(product);
                    return newItem;
                });
        cartItem.setQuantity(quantity);
        cartItem.calculateSubTotal();

        cartItemRepository.save(cartItem);

        cart.calculateTotalPrice();
        Cart updatedCart = cartRepository.save(cart);
        return cartMapper.toDto(updatedCart);
    }


    public void removeProductFromCart(Long productId){
        Cart cart = getOrCreateCartEntity();
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id:" + productId));
        cartItemRepository.findByCartAndProduct(cart, product).ifPresent(item -> {
            cart.getItems().remove(item);
            cartItemRepository.delete(item);
        });
        cart.calculateTotalPrice();
        cartRepository.save(cart);
    }

    private User getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SecureUser secureUser = (SecureUser) authentication.getPrincipal();
        return secureUser.getUser();
    }
}
