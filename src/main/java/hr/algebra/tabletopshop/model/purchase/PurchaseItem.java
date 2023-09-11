package hr.algebra.tabletopshop.model.purchase;

import hr.algebra.tabletopshop.model.items.Item;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@Document("purchaseItems")
@ToString(doNotUseGetters = true)
public class PurchaseItem implements Serializable {
    @Id
    @ToString.Exclude
    private String id;
    
    @ToString.Exclude
    private Integer purchaseItemId;
    
    @DBRef
    private Purchase purchase;
    
    private Item item;
    
    private Integer quantity;
    
    public Double getPurchaseItemTotal() {
        return this.item.getPrice() * this.quantity;
    }
    
    @Builder
    public PurchaseItem(Integer purchaseItemId, Purchase purchase, Item item, Integer quantity) {
        this.purchaseItemId = purchaseItemId;
        this.purchase = purchase;
        this.item = item;
        this.quantity = quantity;
    }
}
