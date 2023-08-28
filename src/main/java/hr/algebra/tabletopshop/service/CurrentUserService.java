package hr.algebra.tabletopshop.service;

import hr.algebra.tabletopshop.model.users.User;
import org.springframework.security.core.Authentication;

public interface CurrentUserService {
    User getCurrentUser();
    
    Authentication getAuthentication();
    
}
