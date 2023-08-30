package hr.algebra.tabletopshop.model.cart;

import hr.algebra.tabletopshop.model.users.User;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(force = true)
@EqualsAndHashCode(doNotUseGetters = true, onlyExplicitlyIncluded = true)
@Document("carts")
public class Cart implements Serializable{
    
    @Id
    private String id;
    
    @EqualsAndHashCode.Include
    private Integer cartId;
    
    @EqualsAndHashCode.Include
    @DBRef
    private User user;
    
    private Double totalPrice;
    
    @DBRef
    private Set<CartItem> cartItems;
    
    @Builder
    public Cart(Integer cartId, User user, Double totalPrice, Set<CartItem> cartItems) {
        this.cartId = cartId;
        this.user = user;
        this.totalPrice = Objects.requireNonNullElse(totalPrice, 0.0);
        this.cartItems = Objects.requireNonNullElse(cartItems, new HashSet<>());
    }
    
    public void addToTotal(Double value) {
        this.totalPrice = this.totalPrice + value;
    }
    
    public void subtractFromTotal(Double value) {
        this.totalPrice = this.totalPrice - value;
    }
    
    public void addItem(CartItem item) {
        cartItems.add(item);
        changeTotal();
    }
    
    public void removeItem(CartItem item) {
        cartItems.remove(item);
        changeTotal();
    }
    
    private void changeTotal() {
        Double tempTotal = 0.0;
        for (var item : cartItems) {
            tempTotal += item.getItem().getPrice() * item.getQuantity();
        }
        totalPrice = tempTotal;
    }
    
    public static Cart copyCart(Cart cart) {
        return new Cart(
                cart.getCartId(),
                cart.getUser(),
                cart.getTotalPrice(),
                cart.getCartItems()
        );
    }
}
