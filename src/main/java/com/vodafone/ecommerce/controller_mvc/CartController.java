package com.vodafone.ecommerce.controller_mvc;

import com.vodafone.ecommerce.model.Cart;
import com.vodafone.ecommerce.model.CartItem;
import com.vodafone.ecommerce.model.SecurityUser;
import com.vodafone.ecommerce.service.CartService;
import com.vodafone.ecommerce.util.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/customer/{customerId}/cart")
@PreAuthorize("hasAuthority('Customer')")
public class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public String getCartById(@PathVariable(name = "customerId") Long customerId,
                              @AuthenticationPrincipal SecurityUser user, Model model) {
        AuthUtil.isNotLoggedInUserThrowException(customerId, user.getUser().getId());
        Cart cart = cartService.getCartByCustomerId(customerId);
        Set<CartItem> cartItems = cart.getCartItems().stream().filter(item -> !item.getProduct().getIsArchived()).collect(Collectors.toSet());;

        model.addAttribute("customerId", customerId);
        model.addAttribute("cart_items", cartItems);
        return "customer-readCartList";
    }

    @PostMapping
    public ResponseEntity<Cart> addCartItem(@PathVariable(name = "customerId") Long customerId,
                                            @RequestBody CartItem cartItem,
                                            @AuthenticationPrincipal SecurityUser user) { //TODO: handle image
        AuthUtil.isNotLoggedInUserThrowException(customerId, user.getUser().getId());
        Cart cartRes = cartService.addCartItem(customerId, cartItem);
        return new ResponseEntity<>(cartRes, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{cartItemId}")
    public ResponseEntity<Cart> updateCartItem(@PathVariable(name = "customerId") Long customerId,
                                               @PathVariable(name = "cartItemId") Long cartItemId,
                                               @RequestBody CartItem cartItem,
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




    @PostMapping
    public String checkoutCart(@PathVariable("customerId") Long customerId,
                               @AuthenticationPrincipal SecurityUser user,
                               BindingResult bindingResult, Model model){
        AuthUtil.isNotLoggedInUserThrowException(customerId, user.getUser().getId());

        if (bindingResult.hasErrors()) {
            Cart cart = cartService.getCartByCustomerId(customerId);
            Set<CartItem> cartItems = cart.getCartItems().stream().filter(item -> !item.getProduct().getIsArchived()).collect(Collectors.toSet());;
            model.addAttribute("customerId", customerId);
            model.addAttribute("cart_items", cartItems);
            return "customer-readCartList";
        }
        Cart cart = cartService.getCartByCustomerId(customerId);
        Set<CartItem> cartItems = cart.getCartItems().stream().filter(item -> !item.getProduct().getIsArchived()).collect(Collectors.toSet());;

        model.addAttribute("customerId", customerId);
        model.addAttribute("cart_items", cartItems);
        return "redirect:/customer/"+customerId+"/order/address";
    }

//    @PostMapping
//    public ResponseEntity<Order> checkoutCart(@PathVariable("customerId") Long customerId,
//                                              @AuthenticationPrincipal SecurityUser user) {
//        AuthUtil.isNotLoggedInUserThrowException(customerId, user.getUser().getId());
//        return new ResponseEntity<>(/*orderService.checkoutCart(customerId)*/null, HttpStatus.CREATED);
//    }

}