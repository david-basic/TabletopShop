package hr.algebra.tabletopshop.controllers;

import hr.algebra.tabletopshop.dto.CartItemDto;
import hr.algebra.tabletopshop.dto.ChangeItemQuantityDto;
import hr.algebra.tabletopshop.dto.RemoveItemFromCartDto;
import hr.algebra.tabletopshop.model.cart.Cart;
import hr.algebra.tabletopshop.model.cart.CartItem;
import hr.algebra.tabletopshop.model.items.Item;
import hr.algebra.tabletopshop.publisher.CustomSpringEventPublisher;
import hr.algebra.tabletopshop.service.CartService;
import hr.algebra.tabletopshop.service.CurrentUserService;
import hr.algebra.tabletopshop.service.ItemService;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Objects;
import java.util.Set;

@Data
@Controller
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {
    private final Cart cart;
    private final ItemService itemService;
    private final CartService cartService;
    private final CustomSpringEventPublisher customSpringEventPublisher;
    private final CurrentUserService currentUserService;
    
    @GetMapping("/get")
    public ModelAndView getCart(Authentication authentication) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("cart", Objects.nonNull(authentication) ? cartService.getCurrentUserCart() : cart);
        mav.addObject("cartItemCount", Objects.nonNull(authentication) ? cartService.getCurrentUserCart().getCartItems().size() : cart.getCartItems().size());
        mav.setViewName("cartPage");
        return mav;
    }
    
    @PostMapping("/auth/addToCart")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView addItemToCartAuth(@ModelAttribute @Valid CartItemDto cartItemDto) {
        ModelAndView mav = new ModelAndView();
        Item item = itemService.getItemById(cartItemDto.getId());
        
        cartService.addItemToCart(item, cartItemDto.getQuantity());
        
        customSpringEventPublisher.publishCustomEvent("Item added to cart by a user!");
        
        mav.setViewName("redirect:/store/homePage");
        
        return mav;
    }
    
    @PostMapping("/anon/addToCart")
    public ModelAndView addItemToCartAnon(@ModelAttribute @Valid CartItemDto cartItemDto) {
        ModelAndView mav = new ModelAndView();
        Item item = itemService.getItemById(cartItemDto.getId());
        Set<CartItem> cartItems = cart.getCartItems();
        if (cartItems.size() == 0) {
            cart.addItem(CartItem.builder().cartItemId(1).item(item).quantity(cartItemDto.getQuantity()).build());
            
        } else {
            cart.getCartItems().stream().filter(it -> it.getItem().equals(item)).findFirst().ifPresentOrElse(i -> {
                i.addQuantity(cartItemDto.getQuantity());
                cart.addToTotal(item.getPrice() * cartItemDto.getQuantity());
            }, () -> cart.addItem(CartItem.builder().cartItemId(cart.getCartItems().size() + 1).item(item).quantity(cartItemDto.getQuantity()).build()));
        }
        
        customSpringEventPublisher.publishCustomEvent("Item added to cart by a anonymous!");
        
        mav.setViewName("redirect:/public/home");
        
        return mav;
    }
    
    @PostMapping("/cartItem/inc")
    @ResponseBody
    public ResponseEntity<ChangeItemQuantityDto> incrementItemQuantity(Authentication authentication, String item_id) {
        Item item = itemService.getItemById(item_id);
        
        if (Objects.nonNull(authentication)) {
            return ResponseEntity.ok(cartService.incrementItemInCart(item));
        } else {
            return cart.getCartItems().stream().filter(it -> it.getItem().equals(item)).findFirst().map(i -> {
                i.incrementQuantity();
                cart.addToTotal(i.getItemPrice());
                return ResponseEntity.ok(new ChangeItemQuantityDto(i.getTotal(), cart.getTotalPrice()));
            }).orElse(ResponseEntity.notFound().build());
        }
    }
    
    @PostMapping("/cartItem/dec")
    @ResponseBody
    public ResponseEntity<ChangeItemQuantityDto> decrementItemQuantity(Authentication authentication, String item_id) {
        Item item = itemService.getItemById(item_id);
        
        if (Objects.nonNull(authentication)) {
            return ResponseEntity.ok(cartService.decrementItemInCart(item));
        } else {
            return cart.getCartItems().stream().filter(it -> it.getItem().equals(item)).findFirst().map(i -> {
                i.decrementQuantity();
                Integer quantity = i.getQuantity();
                if (quantity == 0) {
                    cart.removeItem(i);
                } else {
                    cart.subtractFromTotal(i.getItemPrice());
                }
                return ResponseEntity.ok(new ChangeItemQuantityDto(quantity == 0 ? 0.0 : i.getTotal(), cart.getTotalPrice()));
            }).orElse(ResponseEntity.notFound().build());
        }
    }
    
    @PostMapping("/cartItem/remove")
    @ResponseBody
    public ResponseEntity<RemoveItemFromCartDto> removeItem(Authentication authentication, String item_id) {
        Item item = itemService.getItemById(item_id);
        
        if (Objects.nonNull(authentication)) {
            return ResponseEntity.ok(cartService.removeItemFromCart(item));
        } else {
            return cart.getCartItems().stream().filter(it -> it.getItem().equals(item)).findFirst().map(i -> {
                cart.removeItem(i);
                return ResponseEntity.ok(new RemoveItemFromCartDto(cart.getTotalPrice()));
            }).orElse(ResponseEntity.notFound().build());
        }
    }
}
