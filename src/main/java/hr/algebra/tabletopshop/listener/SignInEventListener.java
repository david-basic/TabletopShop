package hr.algebra.tabletopshop.listener;

import hr.algebra.tabletopshop.event.SignInEvent;
import hr.algebra.tabletopshop.model.logging.LoginLog;
import hr.algebra.tabletopshop.model.users.User;
import hr.algebra.tabletopshop.service.LoginLogService;
import hr.algebra.tabletopshop.service.UtilitiesService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class SignInEventListener implements ApplicationListener<SignInEvent> {
    private final LoginLogService loginLogService;
    private final UtilitiesService utilitiesService;
    
    @Override
    public void onApplicationEvent(@NotNull SignInEvent event) {
        User user = (User) event.getSource();
        loginLogService.saveLoginLog(LoginLog.builder()
                                             .logId(utilitiesService.calculateNextLogIdInSequence())
                                             .user(user)
                                             .loginAt(new Date())
                                             .build());
    }
}
