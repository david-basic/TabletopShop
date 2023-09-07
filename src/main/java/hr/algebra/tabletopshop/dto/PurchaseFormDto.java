package hr.algebra.tabletopshop.dto;

import hr.algebra.tabletopshop.annotations.ZipCodeLength;
import hr.algebra.tabletopshop.model.purchase.PaymentMethod;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PurchaseFormDto {
    
    @NotNull(message = "First name must be input!")
    @Size(min = 2, message = "First name must be at least 2 characters long")
    private String firstName;
    
    @NotNull(message = "Last name must be input!")
    @Size(min = 2, message = "Last name must be at least 2 characters long")
    private String lastName;
    
    @NotNull(message = "Email must be input!")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$", message = "Email must be valid.")
    private String email;
    
    @NotNull(message = "Address must be input!")
    @Size(min = 3, max = 150, message = "Address must be between 3 and 150 characters long")
    private String address1;
    
    @Size(max = 150, message = "Address must be less than 150 characters long")
    private String address2;
    
    @NotNull(message = "County name must be input!")
    @Size(min = 7, max = 23, message = "County name must be between 7 and 23 characters long")
    private String county;
    
    @NotNull(message = "City name must be input!")
    @Size(min = 2, max = 50, message = "City name must be between 2 and 50 characters long")
    private String city;
    
    @NotNull(message = "Zipcode must be input!")
    @Positive(message = "Zipcode can not be a negative number!")
    @ZipCodeLength
    private Integer zipCode;
    
    @NotNull(message = "Payment method must be assigned!")
    private String paymentMethod;
    
    private String userId;
    
}
