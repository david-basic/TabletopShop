package hr.algebra.tabletopshop.annotations;

import hr.algebra.tabletopshop.validators.ZipCodeLengthValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ZipCodeLengthValidator.class)
@Documented
public @interface ZipCodeLength {
    String message() default "Zipcode have exactly 5 digits";
    
    Class<?>[] groups() default {};
    
    Class<? extends Payload>[] payload() default {};
}
