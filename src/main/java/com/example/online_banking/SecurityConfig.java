package com.example.online_banking;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

@Bean
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/", "/login", "/logout", "/css/**", "/js/**").permitAll()
            .anyRequest().authenticated()
        )
     .formLogin(login -> login
    .loginPage("/login")
    .defaultSuccessUrl("/dashboard", true)  // redirects here after successful login
    .permitAll()
)

        .logout(logout -> logout
            .logoutSuccessUrl("/login?logout")
            .permitAll()
        );

    return http.build();
}

    @Bean
    public UserDetailsService users() {
        UserDetails user = User.builder()
            .username("AMAN SHARMA")
            .password("{noop}@Sharma84")
            .roles("USER")
            .build();
        return new InMemoryUserDetailsManager(user);
    }
}
