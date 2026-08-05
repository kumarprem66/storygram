package com.storyverse.identity.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Spring configuration for the Identity module.
 * Enables component scanning and JPA repository detection within the module's packages.
 */
@Configuration
@ComponentScan(basePackages = "com.storyverse.identity")
@EnableJpaRepositories(basePackages = "com.storyverse.identity.adapters.persistence")
public class IdentityModuleConfig {
}
