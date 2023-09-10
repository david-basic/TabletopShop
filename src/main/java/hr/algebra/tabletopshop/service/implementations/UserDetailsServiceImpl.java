package hr.algebra.tabletopshop.service.implementations;

import hr.algebra.tabletopshop.event.SignInEvent;
import hr.algebra.tabletopshop.model.users.User;
import hr.algebra.tabletopshop.repository.UserRepositoryMongo;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    
    private final UserRepositoryMongo userRepositoryMongo;
    private final ApplicationEventPublisher eventPublisher;
    
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepositoryMongo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        eventPublisher.publishEvent(new SignInEvent(user));
        return UserDetailsImpl.build(user);
    }
}
