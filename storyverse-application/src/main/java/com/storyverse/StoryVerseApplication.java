package com.storyverse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * StoryVerse application entry point.
 * Scans all com.storyverse.* packages to discover module configurations.
 */
@SpringBootApplication
public class StoryVerseApplication {

    public static void main(String[] args) {
        SpringApplication.run(StoryVerseApplication.class, args);
    }

}
