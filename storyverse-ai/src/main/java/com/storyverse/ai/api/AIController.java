package com.storyverse.ai.api;

import com.storyverse.ai.application.AIReaderUseCase;
import com.storyverse.ai.application.AIWriterUseCase;
import com.storyverse.shared.api.ApiResponse;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/ai")
public class AIController {
    private final AIWriterUseCase writerUseCase;
    private final AIReaderUseCase readerUseCase;

    public AIController(AIWriterUseCase writerUseCase, AIReaderUseCase readerUseCase) {
        this.writerUseCase = writerUseCase;
        this.readerUseCase = readerUseCase;
    }

    @PostMapping("/writer/continue")
    public ResponseEntity<ApiResponse<Map<String, String>>> continueWriting(@RequestParam @NotBlank String context) {
        String result = writerUseCase.continueWriting(context);
        return ResponseEntity.ok(ApiResponse.success(Map.of("result", result != null ? result : "")));
    }

    @PostMapping("/writer/rewrite")
    public ResponseEntity<ApiResponse<Map<String, String>>> rewrite(@RequestParam @NotBlank String text) {
        String result = writerUseCase.rewrite(text);
        return ResponseEntity.ok(ApiResponse.success(Map.of("result", result != null ? result : "")));
    }

    @PostMapping("/writer/summarize")
    public ResponseEntity<ApiResponse<Map<String, String>>> summarize(@RequestParam @NotBlank String text) {
        String result = writerUseCase.summarize(text);
        return ResponseEntity.ok(ApiResponse.success(Map.of("result", result != null ? result : "")));
    }

    @PostMapping("/writer/generate-title")
    public ResponseEntity<ApiResponse<Map<String, String>>> generateTitle(@RequestParam @NotBlank String description) {
        String result = writerUseCase.generateTitle(description);
        return ResponseEntity.ok(ApiResponse.success(Map.of("result", result != null ? result : "")));
    }

    @PostMapping("/reader/summarize-story")
    public ResponseEntity<ApiResponse<Map<String, String>>> summarizeStory(@RequestParam @NotBlank String content) {
        String result = readerUseCase.summarizeStory(content);
        return ResponseEntity.ok(ApiResponse.success(Map.of("result", result != null ? result : "")));
    }

    @PostMapping("/reader/explain")
    public ResponseEntity<ApiResponse<Map<String, String>>> explain(@RequestParam @NotBlank String text) {
        String result = readerUseCase.explainText(text);
        return ResponseEntity.ok(ApiResponse.success(Map.of("result", result != null ? result : "")));
    }
}
