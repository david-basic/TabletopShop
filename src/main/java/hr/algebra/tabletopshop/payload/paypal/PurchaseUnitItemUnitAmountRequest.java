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
public class PurchaseUnitItemUnitAmountRequest {
    @JsonProperty("currency_code")
    private CurrencyCode currencyCode;
    
    @JsonProperty("value")
    private String value;
}
