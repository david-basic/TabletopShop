package hr.algebra.tabletopshop.repository;

import hr.algebra.tabletopshop.model.users.Role;
import hr.algebra.tabletopshop.model.users.RoleEnum;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RoleRepositoryMongo extends MongoRepository<Role, String> {
    Optional<Role> findByName(RoleEnum name);
}
