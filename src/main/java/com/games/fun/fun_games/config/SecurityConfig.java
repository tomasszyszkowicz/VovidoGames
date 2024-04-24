package com.games.fun.fun_games.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


/**
 * Configuration class for security settings.
 */
@Configuration
public class SecurityConfig {

    /**
     * Configures the security filter chain.
     *
     * @param http the HttpSecurity object to configure
     * @return the configured SecurityFilterChain
     * @throws Exception if an error occurs during configuration
     */
    @SuppressWarnings({ "deprecation", "removal" })
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeRequests(authorize -> authorize
                .requestMatchers("/register", "/css/**", "/js/**", "/images/**").permitAll() // Allow everyone access to these paths
                .requestMatchers("/admin/**").hasAuthority("ROLE_ADMIN") // Only allow users with ROLE_ADMIN to access admin paths
                .anyRequest().authenticated()) // All other requests require authentication
            .formLogin(formLogin -> formLogin
                .loginPage("/login")
                .failureHandler((request, response, exception) -> {
                    String errorMessage = "Invalid username or password.";
                    request.getSession().setAttribute("errorMessage", errorMessage);
                    response.sendRedirect("/login?error");
                })
                .successHandler((request, response, authentication) -> {
                    response.sendRedirect("/home"); // Redirect to home page after successful login
                })
                .permitAll())
            .logout(logout -> logout
                .logoutSuccessUrl("/login?logout")
                .invalidateHttpSession(true)
                .permitAll())
            .exceptionHandling().accessDeniedPage("/access-denied"); // Custom access denied page
        return http.build();
    }

    /**
     * Creates a BCryptPasswordEncoder bean for password encoding.
     *
     * @return the BCryptPasswordEncoder bean
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
