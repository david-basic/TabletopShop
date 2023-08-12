package hr.algebra.tabletopshop.domain.users;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Document("userslist")
public class User implements Serializable {
    
    @Id
    @EqualsAndHashCode.Exclude
    private Integer id;
    
    @NotNull(message = "Username must exist!")
    @Size(min = 5, message = "Username must have 5 characters at least!")
    private String username;
    
    @NotNull(message = "Password must exist!")
    @Size(min = 8, message = "Password must have 8 characters at least!")
    @EqualsAndHashCode.Exclude
    private String password;
    
    @EqualsAndHashCode.Exclude
    @NotNull
    private Boolean enabled;
}
