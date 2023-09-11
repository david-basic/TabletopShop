package hr.algebra.tabletopshop.filter;

import hr.algebra.tabletopshop.event.SignInEvent;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@Order(3)
@RequiredArgsConstructor
public class LogFilter implements Filter {
    private Logger logger = LoggerFactory.getLogger(LogFilter.class);
    
    private final HttpSession httpSession;
    private final ApplicationEventPublisher eventPublisher;
    
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        logger.info("Hello from: " + request.getLocalAddr());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && !authentication.getAuthorities().contains(AuthorityUtils.createAuthorityList("ROLE_ANONYMOUS").get(0))) {
            String username = authentication.getName();
            if (httpSession.getAttribute("userLogged") == null) {
                logger.info("User '{}' logged in from: {}", username, request.getRemoteAddr());
                Map<String, String> loginData = new HashMap<>();
                loginData.put("username", username);
                loginData.put("ipAddress", request.getRemoteAddr());
                eventPublisher.publishEvent(new SignInEvent(loginData));
                httpSession.setAttribute("userLogged", true);
            }
        }
        chain.doFilter(request, response);
    }
}
