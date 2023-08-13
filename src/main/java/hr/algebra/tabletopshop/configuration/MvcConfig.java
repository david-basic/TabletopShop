package hr.algebra.tabletopshop.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/public/home.html").setViewName("home");
        registry.addViewController("/public/browse.html").setViewName("browse");
        registry.addViewController("/auth/login.html").setViewName("login");
        registry.addViewController("/auth/register.html").setViewName("register");
        registry.addViewController("/storeHome/homePage.html").setViewName("homePage");
        registry.addViewController("/storeHome/browse.html").setViewName("browse");
        registry.addViewController("/storeHome/newItem.html").setViewName("newItem");
    }
}
