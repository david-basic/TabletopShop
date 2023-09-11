package hr.algebra.tabletopshop.payload.paypal;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreatePurchaseRequest {
    @JsonProperty("purchase_units")
    @Singular
    private List<PurchaseUnitRequest> purchaseUnits;
    
    @JsonProperty("intent")
    private PayPalIntent intent;
    
    @JsonProperty("application_context")
    private ApplicationContextRequest applicationContext;
}
