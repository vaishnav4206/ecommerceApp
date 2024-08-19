package com.bakerhughes.ecommerce.product.service;

import com.bakerhughes.ecommerce.product.model.CartItem;
import com.bakerhughes.ecommerce.product.model.CartItem;
import com.bakerhughes.ecommerce.product.repository.CartItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartItemService {

    private final CartItemRepository cartItemRepository;

    public CartItemService(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    public List<CartItem> getCartItems(String userId) {
        return cartItemRepository.findByUserId(userId);
    }

    public CartItem addCartItem(CartItem cartItem) {
        return cartItemRepository.save(cartItem);
    }

    public void removeCartItem(Long id) {
        cartItemRepository.deleteById(id);
    }
}
