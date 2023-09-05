package hr.algebra.tabletopshop.dto;

import hr.algebra.tabletopshop.model.purchase.Purchase;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreatePurchaseDto {
    private Purchase purchase;
    private String payPalLink;
}
