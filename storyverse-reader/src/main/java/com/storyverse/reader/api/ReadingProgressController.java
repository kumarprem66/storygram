package com.storyverse.reader.api;

import com.storyverse.reader.application.*;
import com.storyverse.shared.api.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/reading")
public class ReadingProgressController {
    private final StartReadingUseCase startReadingUseCase;
    private final UpdateReadingProgressUseCase updateReadingProgressUseCase;

    public ReadingProgressController(StartReadingUseCase startReadingUseCase, UpdateReadingProgressUseCase updateReadingProgressUseCase) {
        this.startReadingUseCase = startReadingUseCase;
        this.updateReadingProgressUseCase = updateReadingProgressUseCase;
    }

    @PostMapping("/start")
    public ResponseEntity<ApiResponse<Map<String, UUID>>> startReading(@RequestParam UUID storyId, @RequestParam UUID chapterId) {
        UUID progressId = startReadingUseCase.execute(storyId, chapterId);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.created(Map.of("progressId", progressId)));
    }

    @PutMapping("/progress")
    public ResponseEntity<ApiResponse<Void>> updateProgress(@RequestParam UUID storyId, @RequestParam UUID chapterId,
                                                             @RequestParam int chapterIndex, @RequestParam double percentage,
                                                             @RequestParam(defaultValue = "0") long sessionSeconds) {
        updateReadingProgressUseCase.execute(storyId, chapterId, chapterIndex, percentage, sessionSeconds);
        return ResponseEntity.ok(ApiResponse.success(null, "Progress updated"));
    }
}
