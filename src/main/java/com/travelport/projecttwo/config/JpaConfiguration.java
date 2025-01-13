package com.travelport.projecttwo.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.travelport.demospringboot.repository")
@EnableJpaAuditing
public class JpaConfiguration {

    @ConditionalOnMissingBean
    @Bean("auditorAware")
    public AuditorAware<String> auditorAware() {
        return new AuditorAwareImpl();
    }

    @Bean("auditorAware")
    public AuditorAware<String> auditorAware2() {
        return new AuditorAwareImpl();
    }

}
