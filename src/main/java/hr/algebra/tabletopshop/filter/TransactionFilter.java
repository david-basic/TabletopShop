package hr.algebra.tabletopshop.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Order(1)
public class TransactionFilter implements Filter {
    private Logger logger = LoggerFactory.getLogger(TransactionFilter.class);
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        logger.info("Starting a transaction for req : {}", req.getRequestURI());
        chain.doFilter(request, response);
        logger.info("Committing a transaction for req : {}", req.getRequestURI());
    }
}
