package com.storyverse.creator.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan(basePackages = "com.storyverse.creator")
@EnableJpaRepositories(basePackages = "com.storyverse.creator.adapters.persistence")
public class CreatorModuleConfig {
}
