package com.travelport.projecttwo.config;

import com.travelport.projecttwo.security.UserAppDetailsManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

import java.util.List;

@Configuration
public class SecurityConfiguration {

    @Bean
    public UserAppDetailsManager userAppDetailsManager() {
        var manager = new UserAppDetailsManager();
        manager.createUser(new User("admin", "{noop}admin", List.of()));
        return manager;
    }

    @Bean
    public AuthenticationProvider basicProvider() {
        var provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userAppDetailsManager());
        return provider;
    }

    @Bean
    public BasicAuthenticationEntryPoint entryPoint() {
        var entryPoint = new BasicAuthenticationEntryPoint();
        entryPoint.setRealmName("Travelport");
        return entryPoint;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/sales/**").authenticated()
                                .requestMatchers("/purchases/**").authenticated()
                                .requestMatchers(HttpMethod.GET, "/clients/*/sales").authenticated()
                                .anyRequest().permitAll()
                )
                .httpBasic(customizer -> {
                    customizer.authenticationEntryPoint(entryPoint());
                }).build();
    }
}
