package hr.algebra.tabletopshop.service;

import hr.algebra.tabletopshop.model.users.User;

import java.util.List;

public interface UserValidationService {
    String validateDuplicateUser(User userToValidate, List<User> users);
}
