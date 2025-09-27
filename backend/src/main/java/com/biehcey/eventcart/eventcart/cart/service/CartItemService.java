package com.biehcey.eventcart.eventcart.cart.service;

import com.biehcey.eventcart.eventcart.cart.entity.Cart;
import com.biehcey.eventcart.eventcart.cart.entity.CartItem;
import com.biehcey.eventcart.eventcart.cart.repository.CartItemRepository;
import com.biehcey.eventcart.eventcart.product.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CartItemService {
    private final CartItemRepository cartItemRepository;
    
    public CartItem saveOrUpdateCartItem(Cart cart, Product product, int quantity){
        CartItem cartItem = cartItemRepository.findByCartAndProduct(cart, product)
                .orElseGet(() -> {
                    CartItem newItem = new CartItem();
                    newItem.setCart(cart);
                    newItem.setProduct(product);
                    cart.getItems().add(newItem);
                    return newItem;
                });
        cartItem.setQuantity(quantity);
        cartItem.calculateSubTotal();
        return cartItemRepository.save(cartItem);
    }

    public CartItem removeCartItemById(Long cartItemId){
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found by id:" + cartItemId));
        cartItemRepository.delete(cartItem);
        return cartItem;
    }
}
