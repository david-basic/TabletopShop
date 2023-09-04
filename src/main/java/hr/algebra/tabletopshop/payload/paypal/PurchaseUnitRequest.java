package hr.algebra.tabletopshop.payload.paypal;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseUnitRequest {
    @JsonProperty("reference_id")
    private String referenceId;
    
    @JsonProperty("description")
    private String description;
    
    @JsonProperty("items")
    @Singular
    private List<PurchaseUnitItemRequest> items;
    
    @JsonProperty("amount")
    private PurchaseUnitAmountRequest amount;
}
