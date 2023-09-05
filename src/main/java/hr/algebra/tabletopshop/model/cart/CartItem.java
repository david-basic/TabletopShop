package hr.algebra.tabletopshop.model.cart;

import hr.algebra.tabletopshop.model.items.Item;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(doNotUseGetters = true, onlyExplicitlyIncluded = true)
@Document("cartitems")
@ToString(doNotUseGetters = true)
public class CartItem implements Serializable{
    
    @Id
    @ToString.Exclude
    private String id;
    
    @EqualsAndHashCode.Include
    @ToString.Exclude
    private Integer cartItemId;
    
    private Item item;
    
    private Integer quantity;
    
    @DBRef
    private Cart cart;
    
    @Builder
    public CartItem(Integer cartItemId, Item item, Integer quantity, Cart cart) {
        this.cartItemId = cartItemId;
        this.item = item;
        this.quantity = quantity;
        this.cart = cart;
    }
    
    public Double getTotal() {
        return this.item.getPrice() * this.quantity;
    }
    
    public Double getItemPrice() {
        return this.item.getPrice();
    }
    
    public void incrementQuantity() {
        this.quantity += 1;
    }
    
    public void decrementQuantity() {
        this.quantity -= 1;
    }
    
    public void addQuantity(Integer quantity) {
        this.quantity += quantity;
    }
    
}
