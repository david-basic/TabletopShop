package hr.algebra.tabletopshop.service;

import hr.algebra.tabletopshop.model.logging.LoginLog;
import hr.algebra.tabletopshop.model.users.User;

import java.util.Date;
import java.util.List;

public interface LoginLogService {
    void saveLoginLog(LoginLog loginLog);
    
    LoginLog getLoginLogById(String id);
    
    List<LoginLog> getAllBetweenDates(Date startDate, Date endDate);
    
    List<LoginLog> getAllByUser(User user);
    
}
