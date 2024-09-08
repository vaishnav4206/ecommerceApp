package com.bakerhughes.ecommerce.product.controller;

import com.bakerhughes.ecommerce.product.model.CartItem;
import com.bakerhughes.ecommerce.product.service.CartItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartItemController {

    private final CartItemService cartItemService;

    public CartItemController(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    @GetMapping("/{userId}")
    public List<CartItem> getCartItems(@PathVariable String userId) {
        return cartItemService.getCartItems(userId);
    }

    @PostMapping
    public CartItem addCartItem(@RequestBody CartItem cartItem) {
        return cartItemService.addCartItem(cartItem);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeCartItem(@PathVariable Long id) {
        cartItemService.removeCartItem(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{userId}/clear")
    public ResponseEntity<Void> clearCartItems(@PathVariable String userId) {
        cartItemService.clearCartItems(userId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/quantity")
    public ResponseEntity<CartItem> updateCartItemQuantity(
            @PathVariable Long id,
            @RequestBody int quantity) {
        CartItem updatedCartItem = cartItemService.updateCartItemQuantity(id, quantity);
        return ResponseEntity.ok(updatedCartItem);
    }
}
