package com.storyverse.community.api;

import com.storyverse.community.application.RateStoryUseCase;
import com.storyverse.shared.api.ApiResponse;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/stories/{storyId}/ratings")
public class RatingController {
    private final RateStoryUseCase rateStoryUseCase;

    public RatingController(RateStoryUseCase rateStoryUseCase) { this.rateStoryUseCase = rateStoryUseCase; }

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> rate(@PathVariable UUID storyId, @RequestParam @Min(1) @Max(5) int score) {
        rateStoryUseCase.execute(storyId, score);
        return ResponseEntity.ok(ApiResponse.success(null, "Rating saved"));
    }
}
