package com.storyverse.creator.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.Set;

public record UpdateCreatorProfileRequest(
        @NotBlank(message = "Display name is required")
        @Size(max = 100) String displayName,
        @Size(max = 2000) String biography,
        @Size(max = 500) String website,
        @Size(max = 10) String language,
        Set<String> genres,
        Set<String> penNames
) {}
