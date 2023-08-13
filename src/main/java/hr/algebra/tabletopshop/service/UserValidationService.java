package hr.algebra.tabletopshop.service;

import hr.algebra.tabletopshop.domain.users.User;

import java.util.List;

public interface UserValidationService {
    String validateDuplicateUser(User userToValidate, List<User> users);
}
