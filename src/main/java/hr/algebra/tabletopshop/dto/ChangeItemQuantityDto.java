package hr.algebra.tabletopshop.dto;

import lombok.Builder;

@Builder
public record ChangeItemQuantityDto(Double itemTotal, Double cartTotal) {
}
