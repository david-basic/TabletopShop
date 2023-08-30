package hr.algebra.tabletopshop.configuration;

import hr.algebra.tabletopshop.model.cart.Cart;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.SessionScope;

@Configuration
public class CartConfig {
    @Bean("cart")
    @SessionScope
    public Cart cart() {
        return Cart.builder().build();
    }
    
}
