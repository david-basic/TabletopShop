package hr.algebra.tabletopshop.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/", "/home").permitAll()
                        .requestMatchers("/storeHome/homePage.html").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/newBoardgame.html").hasAnyRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        //.successHandler(new MySimpleUrlAuthenticationSuccessHandler())
                        .defaultSuccessUrl("/storeHome/homePage.html", true)
                        .permitAll()
                )
                .logout((logout) -> logout.permitAll());
        
        return http.build();
    }
    
    @Bean
    public UserDetailsService userDetailsService(final AuthenticationManagerBuilder auth) throws Exception {
//        UserDetails user =
//                User.withDefaultPasswordEncoder()
//                        .username("user")
//                        .password("password")
//                        .roles("USER")
//                        .build();
//
//        return new InMemoryUserDetailsManager(user);
//
//        auth
//                .inMemoryAuthentication()
//                .withUser("user")
//                .password(encoder().encode("password"))
//                .roles("USER")
//                .and()
//                .withUser("admin")
//                .password(encoder().encode("admin"))
//                .roles("USER", "ADMIN");
//
//        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        
        final User.UserBuilder userBuilder = User.builder().passwordEncoder(this.encoder()::encode);
        UserDetails user = userBuilder
                .username("user")
                .password("password")
                .roles("USER")
                .build();
        
        UserDetails admin = userBuilder
                .username("admin")
                .password("password")
                .roles("USER", "ADMIN")
                .build();
        
        return new InMemoryUserDetailsManager(user, admin);
    }
    
    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}
