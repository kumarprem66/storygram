package com.storyverse.recommendation.api;

import com.storyverse.recommendation.application.*;
import com.storyverse.recommendation.domain.RecommendationItem;
import com.storyverse.shared.api.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/recommendations")
public class RecommendationController {
    private final GetRecommendationsUseCase getRecommendationsUseCase;
    private final GetSimilarStoriesUseCase getSimilarStoriesUseCase;
    private final SubmitFeedbackUseCase submitFeedbackUseCase;

    public RecommendationController(GetRecommendationsUseCase getRecommendationsUseCase,
                                     GetSimilarStoriesUseCase getSimilarStoriesUseCase,
                                     SubmitFeedbackUseCase submitFeedbackUseCase) {
        this.getRecommendationsUseCase = getRecommendationsUseCase;
        this.getSimilarStoriesUseCase = getSimilarStoriesUseCase;
        this.submitFeedbackUseCase = submitFeedbackUseCase;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<RecommendationItem>>> getRecommendations(
            @RequestParam(defaultValue = "PERSONALIZED") String type,
            @RequestParam(defaultValue = "10") int limit) {
        List<RecommendationItem> items = getRecommendationsUseCase.execute(type, limit);
        return ResponseEntity.ok(ApiResponse.success(items));
    }

    @GetMapping("/similar/{storyId}")
    public ResponseEntity<ApiResponse<List<RecommendationItem>>> getSimilar(
            @PathVariable UUID storyId,
            @RequestParam(defaultValue = "5") int limit) {
        List<RecommendationItem> items = getSimilarStoriesUseCase.execute(storyId, limit);
        return ResponseEntity.ok(ApiResponse.success(items));
    }

    @PostMapping("/feedback")
    public ResponseEntity<ApiResponse<Void>> submitFeedback(@RequestParam UUID storyId, @RequestParam String feedbackType) {
        submitFeedbackUseCase.execute(storyId, feedbackType);
        return ResponseEntity.ok(ApiResponse.success(null, "Feedback recorded"));
    }
}
