package hr.algebra.tabletopshop.model.users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document("roles")
public class Role implements Serializable {
    
    @Id
    private Integer id;
    
    private RoleEnum name;
    
    public Role(RoleEnum name){
        this.name = name;
    }
    
    
}
