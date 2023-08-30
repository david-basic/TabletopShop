package hr.algebra.tabletopshop.configuration;

import hr.algebra.tabletopshop.model.cart.Cart;
import hr.algebra.tabletopshop.model.cart.CartItem;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.SessionScope;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class CartConfig {
    @Bean("cart")
    @SessionScope
    public Cart cart() {
        return Cart.builder().build();
    }
    
}
