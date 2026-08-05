package com.storyverse.reader.api;

import com.storyverse.reader.application.AddBookmarkUseCase;
import com.storyverse.shared.api.ApiResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/bookmarks")
public class BookmarkController {
    private final AddBookmarkUseCase addBookmarkUseCase;

    public BookmarkController(AddBookmarkUseCase addBookmarkUseCase) {
        this.addBookmarkUseCase = addBookmarkUseCase;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Map<String, UUID>>> addBookmark(@RequestParam @NotNull UUID storyId,
                                                                       @RequestParam @NotNull UUID chapterId,
                                                                       @RequestParam(required = false) String note) {
        UUID bookmarkId = addBookmarkUseCase.execute(storyId, chapterId, note);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.created(Map.of("bookmarkId", bookmarkId)));
    }
}
