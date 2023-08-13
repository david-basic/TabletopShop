package hr.algebra.tabletopshop.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterRequest {
    @NotBlank
    @Size(min = 4)
    private String username;
    
    private Set<String> roles;
    
    @NotBlank
    @Size(min = 8)
    private String password;
    
    public UserRegisterRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
