package hr.algebra.tabletopshop.payload.paypal;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseUnitItemResponse {
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("quantity")
    private String quantity;
    
    @JsonProperty("description")
    private String description;
    
    @JsonProperty("category")
    private PurchaseUnitItemCategory category;
    
    @JsonProperty("unit_amount")
    private PurchaseUnitItemUnitAmountResponse unitAmount;
}
