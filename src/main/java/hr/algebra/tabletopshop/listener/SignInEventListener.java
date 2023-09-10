package hr.algebra.tabletopshop.listener;

import hr.algebra.tabletopshop.event.SignInEvent;
import hr.algebra.tabletopshop.exceptions.DbEntityNotFoundException;
import hr.algebra.tabletopshop.model.logging.LoginLog;
import hr.algebra.tabletopshop.model.users.User;
import hr.algebra.tabletopshop.repository.UserRepositoryMongo;
import hr.algebra.tabletopshop.service.LoginLogService;
import hr.algebra.tabletopshop.service.UtilitiesService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class SignInEventListener implements ApplicationListener<SignInEvent> {
    private final LoginLogService loginLogService;
    private final UtilitiesService utilitiesService;
    private final UserRepositoryMongo userRepositoryMongo;
    
    @Override
    public void onApplicationEvent(@NotNull SignInEvent event) {
        Map<String, String> source = (Map<String, String>) event.getSource();
        User user = userRepositoryMongo.findByUsername(source.get("username")).orElseThrow(DbEntityNotFoundException::new);
        String ipAddress = source.get("ipAddress");
        loginLogService.saveLoginLog(LoginLog.builder()
                                             .logId(utilitiesService.calculateNextLogIdInSequence())
                                             .user(user)
                                             .ipAddress(ipAddress)
                                             .loginAt(new Date())
                                             .build());
    }
}
