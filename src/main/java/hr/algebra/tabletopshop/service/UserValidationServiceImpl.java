package hr.algebra.tabletopshop.service;

import hr.algebra.tabletopshop.domain.users.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserValidationServiceImpl implements UserValidationService {
    @Override
    public String validateDuplicateUser(User userToValidate, List<User> users) {
        String message = "";
        
        for (User user : users) {
            if (user.equals(userToValidate)) {
                message = "Username taken!";
                break;
            }
        }
        return message;
    }
}
