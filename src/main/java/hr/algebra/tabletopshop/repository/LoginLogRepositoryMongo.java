package hr.algebra.tabletopshop.repository;

import hr.algebra.tabletopshop.model.logging.LoginLog;
import hr.algebra.tabletopshop.model.users.User;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;
import java.util.List;

public interface LoginLogRepositoryMongo extends MongoRepository<LoginLog, String> {
    @Override
    @NotNull
    List<LoginLog> findAll();
    
    List<LoginLog> findAllByUser(User user);
    
    List<LoginLog> findAllByLoginAt(Date loginAt);
    
    List<LoginLog> findAllByLoginAtBetween(Date startDate, Date endDate);
    
}
