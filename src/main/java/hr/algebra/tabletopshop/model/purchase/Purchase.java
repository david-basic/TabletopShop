package hr.algebra.tabletopshop.model.purchase;

import hr.algebra.tabletopshop.model.users.User;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document("purchases")
@ToString(doNotUseGetters = true)
public class Purchase implements Serializable {
    @Id
    @ToString.Exclude
    private String id;
    
    private Integer purchaseId;
    
    @DBRef
    private User user;
    
    private String paypalId;
    
    private String firstName;
    
    private String lastName;
    
    private String email;
    
    private String address1;
    
    private String address2;
    
    private String county;
    
    private String city;
    
    private Integer zipCode;
    
    private PaymentMethod paymentMethod;
    
    private Double purchaseTotal;
    
    @CreatedDate
    private Date createdAt;
    
    @DBRef
    @ToString.Exclude
    private Set<PurchaseItem> purchaseItems;
    
    @Builder
    public Purchase(Integer purchaseId, User user, String paypalId, String firstName, String lastName, String email, String address1, String address2, String county, String city, Integer zipCode, PaymentMethod paymentMethod, Double purchaseTotal, Date createdAt, Set<PurchaseItem> purchaseItems) {
        this.purchaseId = purchaseId;
        this.user = user;
        this.paypalId = paypalId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.address1 = address1;
        this.address2 = address2;
        this.county = county;
        this.city = city;
        this.zipCode = zipCode;
        this.paymentMethod = paymentMethod;
        this.purchaseTotal = purchaseTotal;
        this.createdAt = createdAt;
        this.purchaseItems = purchaseItems;
    }
}
