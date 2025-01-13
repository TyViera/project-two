package com.travelport.projecttwo.config;

import com.travelport.projecttwo.security.AppEntryPoint;
import com.travelport.projecttwo.security.UserAppDetailManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

@Configuration
public class SecurityConfiguration {

    @Bean
    public UserAppDetailManager userAppDetailManager() {
        var manager = new UserAppDetailManager();
        manager.createUser(new User("user", "{noop}123", List.of()));
        return manager;
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        var provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userAppDetailManager());
        return provider;
    }


    @Bean
    public AppEntryPoint entryPoint() {
        return new AppEntryPoint();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/clients/**").permitAll()
                                .requestMatchers("/products/**").permitAll()
                                .requestMatchers("/sales/**").authenticated()
                                .requestMatchers("/purchases/**").authenticated()
                                .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .build();
    }
}
