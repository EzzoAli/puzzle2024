package example.com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@SpringBootApplication
public class UserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }

    // BCryptPasswordEncoder bean to be used for password encryption
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Security configuration to secure HTTP requests
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)  // Disable CSRF if needed (API use case)
                .headers(headers -> headers
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))  // Allow H2 console access
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/users/register", "/api/users/login", "/h2-console/**").permitAll()  // Public access to register and login
                        .anyRequest().authenticated())  // Protect other endpoints
                .formLogin(form -> form
                        .loginPage("/api/users/login")  // Specify the custom login page
                        .loginProcessingUrl("/login")   // This is where Spring processes the login POST request
                        .defaultSuccessUrl("/api/users/home", true)  // Redirect to home after successful login
                        .failureUrl("/api/users/login?error=true")  // Redirect back to login on failure
                        .permitAll())
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/api/users/login?logout=true")  // Redirect on logout
                        .permitAll())
                .build();
    }
}
