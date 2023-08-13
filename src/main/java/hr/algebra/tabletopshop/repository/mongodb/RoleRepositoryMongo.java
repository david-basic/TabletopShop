package hr.algebra.tabletopshop.repository.mongodb;

import hr.algebra.tabletopshop.domain.users.Role;
import hr.algebra.tabletopshop.domain.users.RoleEnum;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RoleRepositoryMongo extends MongoRepository<Role, String> {
    Optional<Role> findByName(RoleEnum name);
}
