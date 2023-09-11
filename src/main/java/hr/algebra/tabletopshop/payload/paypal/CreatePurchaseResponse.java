package hr.algebra.tabletopshop.payload.paypal;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreatePurchaseResponse {
    
    @JsonProperty("id")
    private String id;
    
    @JsonProperty("create_time")
    private LocalDateTime createTime;
    
    @JsonProperty("update_time")
    private LocalDateTime updateTime;
    
    @JsonProperty("purchase_units")
    @Singular
    private List<PurchaseUnitResponse> purchaseUnits;
    
    @JsonProperty("links")
    @Singular
    private List<HrefResponse> links;
}
