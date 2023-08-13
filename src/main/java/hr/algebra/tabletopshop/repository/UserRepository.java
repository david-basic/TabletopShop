package hr.algebra.tabletopshop.repository;

import hr.algebra.tabletopshop.domain.users.User;

import java.util.List;
@Deprecated
public interface UserRepository {
    void saveNewUser(User user);
    List<User> getAllUsers();
    List<User> filterUsers(User filter);
}
