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
public class PurchaseUnitAmountBreakdownRequest {
    @JsonProperty("item_total")
    private PurchaseUnitAmountBreakdownItemTotalRequest itemTotal;
}
