package com.biehcey.eventcart.eventcart.product.helper;

import com.biehcey.eventcart.eventcart.authentication.entity.SecureUser;
import com.biehcey.eventcart.eventcart.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductSecurity {
    private final ProductRepository repository;

    public boolean isOwner(Long productId, Authentication authentication){
        SecureUser user = (SecureUser) authentication.getPrincipal();
        return repository.findById(productId)
                .map(product -> product.getOwner().getId().equals(user.getUser().getId()))
                .orElse(false);
    }
}
