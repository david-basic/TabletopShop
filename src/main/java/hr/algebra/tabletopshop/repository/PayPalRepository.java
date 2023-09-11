package hr.algebra.tabletopshop.repository;

import hr.algebra.tabletopshop.model.cart.Cart;
import hr.algebra.tabletopshop.payload.paypal.CreatePurchaseResponse;

import java.util.Optional;

public interface PayPalRepository {
    Optional<CreatePurchaseResponse> createOrder(Cart cart);
}
