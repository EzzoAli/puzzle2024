package example.com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@SpringBootApplication
public class UserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Security configuration to use Thymeleaf views for login and home
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable()) // Disable CSRF for APIs

                // Allow public access to the registration and login endpoints
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/users/register", "/api/users/login", "/api/users/home").permitAll() // Public access to registration and login
                        .anyRequest().authenticated() // Secure other endpoints
                )

                // Use Thymeleaf login page and redirect to home page after login
                .formLogin(form -> form
                        .loginPage("/api/users/login") // Use Thymeleaf login page
                        .defaultSuccessUrl("/api/users/home", true) // Redirect to home page after login
                        .permitAll()
                )

                .build();
    }
}
