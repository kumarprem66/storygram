package com.storyverse.story.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateStoryRequest(
        @NotBlank(message = "Title is required")
        @Size(max = 200, message = "Title must not exceed 200 characters")
        String title,

        @Size(max = 2000, message = "Description must not exceed 2000 characters")
        String description,

        @NotNull(message = "Genre is required")
        String genre,

        @NotBlank(message = "Language is required")
        @Size(max = 10, message = "Language code must not exceed 10 characters")
        String language
) {}
