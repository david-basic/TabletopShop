package hr.algebra.tabletopshop.service;

import hr.algebra.tabletopshop.dto.ChangeItemQuantityDto;
import hr.algebra.tabletopshop.dto.RemoveItemFromCartDto;
import hr.algebra.tabletopshop.model.cart.Cart;
import hr.algebra.tabletopshop.model.items.Item;
import hr.algebra.tabletopshop.model.users.User;

public interface CartService {
    Cart getCurrentUserCart();
    void saveCart(Cart cart);
    
    void addItemToCart(Item item, Integer quantity);
    
    void cleanCart();
    
    Cart getCartByUser(User user);
    
    Cart getCartById(String id);
    
    Cart getCartByItemId(Integer id);
    
    RemoveItemFromCartDto removeItemFromCart(Item item);
    
    ChangeItemQuantityDto incrementItemInCart(Item item);
    
    ChangeItemQuantityDto decrementItemInCart(Item item);
    
    void deleteCartByUser(User user);
}
