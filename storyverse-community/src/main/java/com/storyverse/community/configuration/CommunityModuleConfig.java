package com.storyverse.community.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan(basePackages = "com.storyverse.community")
@EnableJpaRepositories(basePackages = "com.storyverse.community.adapters.persistence")
public class CommunityModuleConfig {
}
