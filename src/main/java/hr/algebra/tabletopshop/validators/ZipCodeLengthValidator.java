package hr.algebra.tabletopshop.validators;

import hr.algebra.tabletopshop.annotations.ZipCodeLength;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ZipCodeLengthValidator implements ConstraintValidator<ZipCodeLength, Integer> {
    @Override
    public void initialize(ZipCodeLength constraintAnnotation) {
    }
    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null) {
            return true;
        }
        
        String stringValue = Integer.toString(value);
        return stringValue.length() == 5;
    }
}
