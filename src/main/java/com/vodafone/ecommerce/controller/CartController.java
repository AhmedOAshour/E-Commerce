package com.vodafone.ecommerce.controller;

import com.vodafone.ecommerce.model.Cart;
import com.vodafone.ecommerce.model.CartItem;
import com.vodafone.ecommerce.model.SecurityUser;
import com.vodafone.ecommerce.model.dto.CartItemDTO;
import com.vodafone.ecommerce.service.CartService;
import com.vodafone.ecommerce.util.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

//@RestController
@RequestMapping("/customer/{customerId}/cart")
@PreAuthorize("hasAuthority('Customer')")
public class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<Cart> getCartById(@PathVariable(name = "customerId") Long customerId,
                                            @AuthenticationPrincipal SecurityUser user) {
        AuthUtil.isNotLoggedInUserThrowException(customerId, user.getUser().getId());
        return new ResponseEntity<>(cartService.getCartByCustomerId(customerId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Cart> addCartItem(@PathVariable(name = "customerId") Long customerId,
                                            @RequestBody CartItem cartItem,
                                            @AuthenticationPrincipal SecurityUser user) {
        AuthUtil.isNotLoggedInUserThrowException(customerId, user.getUser().getId());
        CartItemDTO cartItemDTO = new CartItemDTO();
        cartItemDTO.setQuantity(cartItem.getQuantity());
        cartItemDTO.setProductId(cartItemDTO.getProductId());
        Cart cartRes = cartService.addCartItem(customerId, cartItemDTO);
        return new ResponseEntity<>(cartRes, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{cartItemId}")
    public ResponseEntity<Cart> updateCartItem(@PathVariable(name = "customerId") Long customerId,
                                               @PathVariable(name = "cartItemId") Long cartItemId,
                                               @RequestBody CartItemDTO cartItem,
                                               @AuthenticationPrincipal SecurityUser user) {
        AuthUtil.isNotLoggedInUserThrowException(customerId, user.getUser().getId());
        Cart cartRes = cartService.updateCartItemQuantity(cartItem, customerId, cartItemId);
        return new ResponseEntity<>(cartRes, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Cart> deleteCart(@PathVariable(name = "customerId") Long customerId,
                                           @AuthenticationPrincipal SecurityUser user) {
        AuthUtil.isNotLoggedInUserThrowException(customerId, user.getUser().getId());
        return new ResponseEntity<>(cartService.deleteAllCartItems(customerId), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{cartItemId}")
    public ResponseEntity<Cart> deleteCartItem(@PathVariable(name = "customerId") Long customerId,
                                               @PathVariable(name = "cartItemId") Long cartItemId,
                                               @AuthenticationPrincipal SecurityUser user) {
        AuthUtil.isNotLoggedInUserThrowException(customerId, user.getUser().getId());
        return new ResponseEntity<>(cartService.deleteCartItem(customerId, cartItemId), HttpStatus.OK);
    }
}
