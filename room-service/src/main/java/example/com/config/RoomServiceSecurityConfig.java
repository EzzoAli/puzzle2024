package example.com.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class RoomServiceSecurityConfig {

    @Bean
    public SecurityFilterChain roomServiceSecurityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable) // Disable CSRF for simplicity
                .headers(headers -> headers
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)) // Allow H2 console access
                .authorizeHttpRequests(auth -> auth
                        // Permit all requests to error page and static resources (CSS, JS)
                        .requestMatchers("/error", "/css/**", "/js/**", "/images/**", "/h2-console/**").permitAll()
                        .requestMatchers("/api/rooms/**").authenticated() // Require authentication for API endpoints
                        .anyRequest().permitAll()) // Allow other requests
                .formLogin(form -> form
                        .loginPage("/login")  // Specify a custom login page if needed
                        .defaultSuccessUrl("/rooms", true)  // Redirect to the room list page after successful login
                        .permitAll())
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout=true") // Redirect to login page after logout
                        .permitAll())
                .build();
    }
}