package com.biehcey.eventcart.eventcart.cart.service;

import com.biehcey.eventcart.eventcart.authentication.entity.User;
import com.biehcey.eventcart.eventcart.authentication.service.UserService;
import com.biehcey.eventcart.eventcart.cart.mapper.CartMapper;
import com.biehcey.eventcart.eventcart.cart.dto.CartDto;
import com.biehcey.eventcart.eventcart.cart.entity.Cart;
import com.biehcey.eventcart.eventcart.cart.entity.CartItem;
import com.biehcey.eventcart.eventcart.cart.repository.CartRepository;
import com.biehcey.eventcart.eventcart.product.entity.Product;
import com.biehcey.eventcart.eventcart.product.service.ProductService;
import com.biehcey.eventcart.eventcart.util.OrderCreatedDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final ProductService productService;
    private final CartMapper cartMapper;
    private final UserService userService;
    private final CartItemService cartItemService;

    public CartDto getCartDto(){
        return cartMapper.toDto(findOrCreateCart());
    }

    public Cart findOrCreateCart(){
        User currentUser = userService.getCurrentUser();
        return cartRepository.findByUser(currentUser).orElseGet(() -> {
            Cart cart = new Cart();
            cart.setUser(currentUser);
            cart.setTotalPrice(BigDecimal.ZERO);
            return cartRepository.save(cart);
        });
    }

    public CartDto addProductToCart(Long productId, int quantity){
        Cart cart = findOrCreateCart();
        Product product = productService.findProductById(productId);
        productService.validateStockSufficient(productId, quantity);
        CartItem cartItem = cartItemService.saveOrUpdateCartItem(cart, product, quantity);
        cart.calculateTotalPrice();
        Cart updatedCart = cartRepository.save(cart);
        return cartMapper.toDto(updatedCart);
    }


    public void removeCartItemById(Long cartItemId){
        Cart cart = findOrCreateCart();
        CartItem removedItem = cartItemService.removeCartItemById(cartItemId);
        cart.getItems().remove(removedItem);
        cart.calculateTotalPrice();
        cartRepository.save(cart);
    }
    @Transactional
    @KafkaListener(topics = {"order-created-topic-v2"}, groupId = "cart-consumer-group-v2")
    public void clearCartAfterOrder(OrderCreatedDto orderCreatedEvent){
        Long cartId = orderCreatedEvent.getCartId();
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found with id:" + cartId));
        cart.getItems().clear();
        cart.setTotalPrice(BigDecimal.ZERO);
        cartRepository.save(cart);
    }
}
