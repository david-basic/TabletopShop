package hr.algebra.tabletopshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class TabletopShopApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(TabletopShopApplication.class, args);
    }
    
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(TabletopShopApplication.class);
    }
}
