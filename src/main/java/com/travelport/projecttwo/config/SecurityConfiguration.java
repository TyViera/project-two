package com.travelport.projecttwo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {

    @Bean
    public UserDetailsManager userAppDetailsManager() {
        var manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("user").password("{noop}password").roles("ADMIN").build());
        manager.createUser(User.withUsername("abc").password("{noop}password").roles("USER").build());
        return manager;
    }

    @Bean
    public AuthenticationEntryPoint entryPoint() {
        var entryPoint = new BasicAuthenticationEntryPoint();
        entryPoint.setRealmName("Travelport");
        return entryPoint;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authorizeHttpRequests(rq -> rq.anyRequest().authenticated())
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(customizer -> customizer.authenticationEntryPoint(entryPoint()))
                .build();
    }

}
