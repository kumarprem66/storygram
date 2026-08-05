package com.storyverse.community.api;

import com.storyverse.community.application.AddCommentUseCase;
import com.storyverse.shared.api.ApiResponse;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/stories/{storyId}/comments")
public class CommentController {
    private final AddCommentUseCase addCommentUseCase;

    public CommentController(AddCommentUseCase addCommentUseCase) { this.addCommentUseCase = addCommentUseCase; }

    @PostMapping
    public ResponseEntity<ApiResponse<Map<String, UUID>>> addComment(@PathVariable UUID storyId,
                                                                      @RequestParam(required = false) UUID parentCommentId,
                                                                      @RequestParam @NotBlank String content) {
        UUID commentId = addCommentUseCase.execute(storyId, parentCommentId, content);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.created(Map.of("commentId", commentId)));
    }
}
