package com.storyverse.story.api;

import com.storyverse.shared.api.ApiResponse;
import com.storyverse.story.api.dto.CreateChapterRequest;
import com.storyverse.story.api.dto.UpdateChapterRequest;
import com.storyverse.story.application.CreateChapterUseCase;
import com.storyverse.story.application.DeleteChapterUseCase;
import com.storyverse.story.application.UpdateChapterUseCase;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

/**
 * REST controller for chapter management within a story.
 */
@RestController
@RequestMapping("/api/v1/stories/{storyId}/chapters")
public class ChapterController {

    private final CreateChapterUseCase createChapterUseCase;
    private final UpdateChapterUseCase updateChapterUseCase;
    private final DeleteChapterUseCase deleteChapterUseCase;

    public ChapterController(CreateChapterUseCase createChapterUseCase,
                             UpdateChapterUseCase updateChapterUseCase,
                             DeleteChapterUseCase deleteChapterUseCase) {
        this.createChapterUseCase = createChapterUseCase;
        this.updateChapterUseCase = updateChapterUseCase;
        this.deleteChapterUseCase = deleteChapterUseCase;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Map<String, UUID>>> createChapter(@PathVariable UUID storyId,
                                                                         @Valid @RequestBody CreateChapterRequest request) {
        UUID chapterId = createChapterUseCase.execute(storyId, request.title(), request.content());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.created(Map.of("chapterId", chapterId)));
    }

    @PutMapping("/{chapterId}")
    public ResponseEntity<ApiResponse<Void>> updateChapter(@PathVariable UUID storyId,
                                                            @PathVariable UUID chapterId,
                                                            @Valid @RequestBody UpdateChapterRequest request) {
        updateChapterUseCase.execute(storyId, chapterId, request.title(), request.content());
        return ResponseEntity.ok(ApiResponse.success(null, "Chapter updated successfully"));
    }

    @DeleteMapping("/{chapterId}")
    public ResponseEntity<ApiResponse<Void>> deleteChapter(@PathVariable UUID storyId,
                                                            @PathVariable UUID chapterId) {
        deleteChapterUseCase.execute(storyId, chapterId);
        return ResponseEntity.ok(ApiResponse.noContent());
    }
}
