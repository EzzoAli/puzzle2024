package example.com.config;  // Place in a security or config package

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class UserServiceSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, UserDetailsService userDetailsService) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable) // Disable CSRF for simplicity (enable in production)
                .headers(headers -> headers
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)) // Allow H2 console access
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/users/register", "/api/users/login", "/h2-console/**").permitAll()  // Allow public access to these endpoints
                        .anyRequest().authenticated()) // Protect all other endpoints
                .formLogin(form -> form
                        .loginPage("/api/users/login")  // Custom login page
                        .loginProcessingUrl("/login")   // Spring Security handles the login POST request here
                        .defaultSuccessUrl("/api/users/home", true)  // Redirect to home after successful login
                        .failureUrl("/api/users/login?error=true")  // Redirect to login page on failure
                        .permitAll())
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/api/users/login?logout=true")  // Redirect to login page after logout
                        .permitAll())
                .userDetailsService(userDetailsService)  // Use custom UserDetailsService for authentication
                .build();
    }
}
