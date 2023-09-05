package hr.algebra.tabletopshop.converter;

import hr.algebra.tabletopshop.model.purchase.PaymentMethod;
import jakarta.validation.constraints.NotNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class PaymentMethodStringToEnumConverter implements Converter<String, PaymentMethod> {
    @Override
    public PaymentMethod convert(@NotNull String source) {
        try {
            return PaymentMethod.valueOf(source);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid Payment method: " + source);
        }
    }
}
