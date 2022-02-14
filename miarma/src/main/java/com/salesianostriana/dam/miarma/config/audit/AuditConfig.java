package com.salesianostriana.dam.miarma.config.audit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class AuditConfig {

    @Bean
    public com.salesianostriana.dam.miarma.config.audit.SpringSecurityAuditorAware auditorProvider() {
        return new com.salesianostriana.dam.miarma.config.audit.SpringSecurityAuditorAware();
    }


}
