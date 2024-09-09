package com.bakerhughes.ecommerce.product.service;

import com.bakerhughes.ecommerce.product.exception.CartItemNotFoundException;
import com.bakerhughes.ecommerce.product.model.CartItem;
import com.bakerhughes.ecommerce.product.repository.CartItemRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartItemService {

    private final CartItemRepository cartItemRepository;
    private final OrderService orderService;

    public CartItemService(CartItemRepository cartItemRepository, OrderService orderService) {
        this.cartItemRepository = cartItemRepository;
        this.orderService = orderService;
    }

    public List<CartItem> getCartItems(String userId) {
        return cartItemRepository.findByUserId(userId);
    }

    public CartItem addCartItem(CartItem cartItem) {
        // Check if the product already exists in the cart for the user
        CartItem existingCartItem = cartItemRepository.findByUserIdAndProductId(cartItem.getUserId(), cartItem.getProductId());

        if (existingCartItem != null) {
            // If the product exists, update the quantity
            existingCartItem.setQuantity(existingCartItem.getQuantity() + cartItem.getQuantity());
            return cartItemRepository.save(existingCartItem);
        } else {
            // Otherwise, add a new item
            return cartItemRepository.save(cartItem);
        }
    }

    public void removeCartItem(Long id) {
        cartItemRepository.deleteById(id);
    }

    public void clearCartItems(String userId) {
        List<CartItem> cartItems = getCartItems(userId);
        cartItems.forEach(cartItem -> removeCartItem(cartItem.getId()));
    }

    @Transactional
    public CartItem updateCartItemQuantity(Long id, int quantity) {
        CartItem cartItem = cartItemRepository.findById(id)
                .orElseThrow(() -> new CartItemNotFoundException("CartItem not found with id: " + id));
        cartItem.setQuantity(quantity);

        return cartItemRepository.save(cartItem);
    }
}
