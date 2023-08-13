package hr.algebra.tabletopshop.configuration;

import hr.algebra.tabletopshop.service.UserDetailsServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@AllArgsConstructor
public class WebSecurityConfig {
    
    private UserDetailsServiceImpl userDetailsService;
    
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(bCryptPasswordEncoder());
        
        return authProvider;
    }
    
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/storeHome/**").authenticated()
                        .requestMatchers("/public/**", "/auth/**", "/resources/**", "/css/**", "/js/**", "/images/**").permitAll()
                        .anyRequest().permitAll()
                )
                .formLogin((form) -> form
//                        .loginPage("/auth/login.html")
//                        .loginProcessingUrl("/login")
                        .loginPage("/auth/login.html")
                        .loginProcessingUrl("/auth/signin")
                        .defaultSuccessUrl("/storeHome/homePage.html", true) // true mi treba ovdje jer inace mi prikaze jquery ili bootstrap response
                        .permitAll()
                )
                .logout(LogoutConfigurer::permitAll);
        
        http.authenticationProvider(authenticationProvider());
        
        return http.build();
    }
    
//    @Bean
//    public UserDetailsService jdbcUserDetailsService(DataSource dataSource) {
//        String usersByUsernameQuery = "select username, password, enabled from users where username = ?";
//        String authsByUsernameQuery = "select username, authority from authorities where username = ?";
//
//
//        JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
//
//        users.setUsersByUsernameQuery(usersByUsernameQuery);
//        users.setAuthoritiesByUsernameQuery(authsByUsernameQuery);
//
//        return users;
//    }
    
    @Bean
    public PasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
