package hr.algebra.tabletopshop.model.users;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(doNotUseGetters = true)
@Document("users")
public class User implements Serializable {
    
    @Id
    @EqualsAndHashCode.Exclude
    private Integer id;
    
    @NotNull(message = "Username must exist!")
    @Size(min = 4, message = "Username must have 4 characters at least!")
    private String username;
    
    @NotNull(message = "Password must exist!")
    @Size(min = 8, message = "Password must have 8 characters at least!")
    @EqualsAndHashCode.Exclude
    private String password;
    
    @NotNull
    @DBRef
    private Set<Role> roles = new HashSet<>();
    
    public User(@NotNull String username, @NotNull String password) {
        this.username = username;
        this.password = password;
    }
    
}
