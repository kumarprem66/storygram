package com.storyverse.story.api;

import com.storyverse.shared.api.ApiResponse;
import com.storyverse.story.api.dto.*;
import com.storyverse.story.application.*;
import com.storyverse.story.domain.Story;
import com.storyverse.story.domain.vo.AgeRating;
import com.storyverse.story.domain.vo.Genre;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

/**
 * REST controller for story management.
 */
@RestController
@RequestMapping("/api/v1/stories")
public class StoryController {

    private final CreateStoryUseCase createStoryUseCase;
    private final UpdateStoryUseCase updateStoryUseCase;
    private final PublishStoryUseCase publishStoryUseCase;
    private final ArchiveStoryUseCase archiveStoryUseCase;
    private final GetStoryUseCase getStoryUseCase;

    public StoryController(CreateStoryUseCase createStoryUseCase,
                           UpdateStoryUseCase updateStoryUseCase,
                           PublishStoryUseCase publishStoryUseCase,
                           ArchiveStoryUseCase archiveStoryUseCase,
                           GetStoryUseCase getStoryUseCase) {
        this.createStoryUseCase = createStoryUseCase;
        this.updateStoryUseCase = updateStoryUseCase;
        this.publishStoryUseCase = publishStoryUseCase;
        this.archiveStoryUseCase = archiveStoryUseCase;
        this.getStoryUseCase = getStoryUseCase;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Map<String, UUID>>> createStory(@Valid @RequestBody CreateStoryRequest request) {
        Genre genre = Genre.valueOf(request.genre().toUpperCase());
        UUID storyId = createStoryUseCase.execute(request.title(), request.description(), genre, request.language());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.created(Map.of("storyId", storyId)));
    }

    @GetMapping("/{storyId}")
    public ResponseEntity<ApiResponse<StoryResponse>> getStory(@PathVariable UUID storyId) {
        Story story = getStoryUseCase.execute(storyId);
        return ResponseEntity.ok(ApiResponse.success(toResponse(story)));
    }

    @PutMapping("/{storyId}")
    public ResponseEntity<ApiResponse<Void>> updateStory(@PathVariable UUID storyId,
                                                          @Valid @RequestBody UpdateStoryRequest request) {
        Genre genre = request.genre() != null ? Genre.valueOf(request.genre().toUpperCase()) : null;
        AgeRating ageRating = request.ageRating() != null ? AgeRating.valueOf(request.ageRating().toUpperCase()) : null;
        updateStoryUseCase.execute(storyId, request.title(), request.subtitle(), request.description(),
                genre, request.tags(), request.language(), ageRating);
        return ResponseEntity.ok(ApiResponse.success(null, "Story updated successfully"));
    }

    @PostMapping("/{storyId}/publish")
    public ResponseEntity<ApiResponse<Void>> publishStory(@PathVariable UUID storyId) {
        publishStoryUseCase.execute(storyId);
        return ResponseEntity.ok(ApiResponse.success(null, "Story published successfully"));
    }

    @PostMapping("/{storyId}/archive")
    public ResponseEntity<ApiResponse<Void>> archiveStory(@PathVariable UUID storyId) {
        archiveStoryUseCase.execute(storyId);
        return ResponseEntity.ok(ApiResponse.success(null, "Story archived successfully"));
    }

    private StoryResponse toResponse(Story story) {
        return new StoryResponse(
                story.getId(),
                story.getAuthorId(),
                story.getTitle(),
                story.getSubtitle(),
                story.getDescription(),
                story.getGenre() != null ? story.getGenre().name() : null,
                story.getTags(),
                story.getLanguage(),
                story.getAgeRating() != null ? story.getAgeRating().name() : null,
                story.getVisibility() != null ? story.getVisibility().name() : null,
                story.getStatus() != null ? story.getStatus().name() : null,
                story.getCoverImageUrl(),
                story.getTotalWordCount().getValue(),
                story.getTotalWordCount().estimatedReadingTimeMinutes(),
                story.getPublishedAt(),
                story.getCreatedAt(),
                story.getUpdatedAt()
        );
    }
}
