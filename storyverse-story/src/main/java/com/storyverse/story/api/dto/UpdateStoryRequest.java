package com.storyverse.story.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record UpdateStoryRequest(
        @NotBlank(message = "Title is required")
        @Size(max = 200, message = "Title must not exceed 200 characters")
        String title,

        @Size(max = 200, message = "Subtitle must not exceed 200 characters")
        String subtitle,

        @Size(max = 2000, message = "Description must not exceed 2000 characters")
        String description,

        String genre,

        Set<String> tags,

        @Size(max = 10, message = "Language code must not exceed 10 characters")
        String language,

        String ageRating
) {}
