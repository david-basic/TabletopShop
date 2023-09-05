package hr.algebra.tabletopshop.configuration;

import hr.algebra.tabletopshop.converter.PaymentMethodStringToEnumConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/public/home").setViewName("home");
        registry.addViewController("/public/browse").setViewName("browse");
        registry.addViewController("/auth/login").setViewName("login");
        registry.addViewController("/store/homePage").setViewName("homePage");
        registry.addViewController("/store/browse").setViewName("browse");
        registry.addViewController("/store/admin/newItem").setViewName("homePage");
    }
    
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new PaymentMethodStringToEnumConverter());
    }
}
