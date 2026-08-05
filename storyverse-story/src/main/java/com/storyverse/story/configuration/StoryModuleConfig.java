package com.storyverse.story.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Spring configuration for the Story module.
 */
@Configuration
@ComponentScan(basePackages = "com.storyverse.story")
@EnableJpaRepositories(basePackages = "com.storyverse.story.adapters.persistence")
public class StoryModuleConfig {
}
