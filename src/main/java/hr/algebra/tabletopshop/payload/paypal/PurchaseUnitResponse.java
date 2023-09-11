package hr.algebra.tabletopshop.payload.paypal;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseUnitResponse {
    
    @JsonProperty("reference_id")
    private String referenceId;
    
    @JsonProperty("description")
    private String description;
    
    @JsonProperty("items")
    private List<PurchaseUnitItemResponse> items;
    
    @JsonProperty("amount")
    private PurchaseUnitAmountResponse amount;
    
}
