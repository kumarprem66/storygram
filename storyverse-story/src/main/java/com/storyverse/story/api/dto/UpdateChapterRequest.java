package com.storyverse.story.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateChapterRequest(
        @NotBlank(message = "Chapter title is required")
        @Size(max = 200, message = "Title must not exceed 200 characters")
        String title,

        @NotBlank(message = "Chapter content is required")
        String content
) {}
