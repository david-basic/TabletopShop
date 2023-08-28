package hr.algebra.tabletopshop.service.implementations;

import hr.algebra.tabletopshop.model.users.Role;
import hr.algebra.tabletopshop.model.users.RoleEnum;
import hr.algebra.tabletopshop.model.users.User;
import hr.algebra.tabletopshop.service.CurrentUserService;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@NoArgsConstructor
public class CurrentUserServiceImpl implements CurrentUserService {
    @Override
    public User getCurrentUser() {
        Authentication authentication = getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            // user is not authenticated!
            return null;
        }
        
        Object principal = authentication.getPrincipal();
        
        if (principal instanceof UserDetailsImpl) {
            // user is authenticated!
            UserDetailsImpl princ = (UserDetailsImpl) principal;
            Set<Role> roles = new HashSet<>();
            int id = 1;
            for (var role : princ.getRoles()) {
                String auth = role.getAuthority();
                RoleEnum roleEnum = RoleEnum.valueOf(auth);
                
                roles.add(new Role(id, roleEnum));
                id++;
            }
            User user = new User(princ.getId(), princ.getUsername(), princ.getPassword(), roles);
            System.out.println(user);
            return user;
        } else {
            // user is anonymous or unknown principal
            return null;
        }
    }
    
    @Override
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
