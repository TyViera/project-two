package com.travelport.projecttwo.config;

import com.travelport.projecttwo.security.AppEntryPoint;
import com.travelport.projecttwo.security.UserAppDetailsManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

@Configuration
public class SecurityConfig {

  @Bean
  public UserAppDetailsManager userAppDetailsManager () {
    var manager = new UserAppDetailsManager();
    manager.createUser(new User(
        "carlos",
        "{bcrypt}$2a$12$PD5Z3mmcPp73GKn6uHuXneYyCvUlsk/IyvlqA7PAi1TwNty3hm6hO",
        List.of()
    ));
    manager.createUser(new User(
        "admin",
        "{bcrypt}$2a$12$/n8txYvCn5C88Dunwvb9AON2BJhqFY/K4Ve9IPUILdtCjPVjFBwUa",
        List.of(new SimpleGrantedAuthority("ROLE_ADMIN"))
    ));

    return manager;
  }

  @Bean
  public AuthenticationProvider basicProvider () {
    var provider = new DaoAuthenticationProvider();
    provider.setUserDetailsService(userAppDetailsManager());
    return provider;
  }

  @Bean
  public AppEntryPoint entryPoint () {
    return new AppEntryPoint();
  }

  @Bean
  public SecurityFilterChain securityFilterChain (HttpSecurity httpSecurity) throws Exception {
    return httpSecurity
        .csrf(AbstractHttpConfigurer::disable)
        .cors(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(authorizeRequests -> authorizeRequests
            .requestMatchers("/clients/{id}/sales").authenticated()
            .requestMatchers("/sales/**").authenticated()
            .requestMatchers("/purchases").authenticated()
            .anyRequest().permitAll())
        .httpBasic(customizer -> customizer.authenticationEntryPoint(entryPoint()))
        .build();
  }

}
