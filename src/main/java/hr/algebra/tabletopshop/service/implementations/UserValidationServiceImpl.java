package hr.algebra.tabletopshop.service.implementations;

import hr.algebra.tabletopshop.model.users.User;
import hr.algebra.tabletopshop.service.UserValidationService;
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
