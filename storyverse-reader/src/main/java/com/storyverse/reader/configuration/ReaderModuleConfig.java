package com.storyverse.reader.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan(basePackages = "com.storyverse.reader")
@EnableJpaRepositories(basePackages = "com.storyverse.reader.adapters.persistence")
public class ReaderModuleConfig {
}
