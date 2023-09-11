package hr.algebra.tabletopshop.service.implementations;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hr.algebra.tabletopshop.model.users.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@EqualsAndHashCode
@Data
public class UserDetailsImpl implements UserDetails {
    
    @Serial
    private static final long serialVersionUID = 1L;
    
    @EqualsAndHashCode.Include
    private Integer id;
    
    @EqualsAndHashCode.Exclude
    private String username;
    
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    private String password;
    
    @EqualsAndHashCode.Exclude
    private Collection<? extends GrantedAuthority> roles;
    
    public static UserDetailsImpl build(User user) {
        //System.out.println(user);
        Set<GrantedAuthority> roles = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toSet());
        
        return new UserDetailsImpl(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                roles);
    }
    
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }
    
    @Override
    public String getPassword() {
        return password;
    }
    
    @Override
    public String getUsername() {
        return username;
    }
    
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    
    @Override
    public boolean isEnabled() {
        return true;
    }
}
