package hr.algebra.tabletopshop.model.users;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document("roles")
@ToString(doNotUseGetters = true)
public class Role implements Serializable {
    
    @Id
    @ToString.Exclude
    private Integer id;
    
    private RoleEnum name;
    
    public Role(RoleEnum name){
        this.name = name;
    }
    
    
}
