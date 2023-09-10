package hr.algebra.tabletopshop.service.implementations;

import hr.algebra.tabletopshop.exceptions.DbEntityNotFoundException;
import hr.algebra.tabletopshop.model.logging.LoginLog;
import hr.algebra.tabletopshop.model.users.User;
import hr.algebra.tabletopshop.repository.LoginLogRepositoryMongo;
import hr.algebra.tabletopshop.service.LoginLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LoginLogServiceImpl implements LoginLogService {
    private final LoginLogRepositoryMongo loginLogRepositoryMongo;
    
    @Override
    public void saveLoginLog(LoginLog loginLog) {
        loginLogRepositoryMongo.save(loginLog);
    }
    
    @Override
    public LoginLog getLoginLogById(String id) {
        return loginLogRepositoryMongo.findById(id).orElseThrow(DbEntityNotFoundException::new);
    }
    
    @Override
    public List<LoginLog> getAllBetweenDates(Date startDate, Date endDate) {
        return loginLogRepositoryMongo.findAllByLoginAtBetween(startDate, endDate);
    }
    
    @Override
    public List<LoginLog> getAllByUser(User user) {
        return loginLogRepositoryMongo.findAllByUser(user);
    }
}
