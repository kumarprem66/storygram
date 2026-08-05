package com.storyverse.moderation.api;

import com.storyverse.moderation.application.*;
import com.storyverse.shared.api.ApiResponse;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/moderation")
public class ModerationController {
    private final ReportContentUseCase reportContentUseCase;
    private final ResolveModerationCaseUseCase resolveModerationCaseUseCase;

    public ModerationController(ReportContentUseCase reportContentUseCase, ResolveModerationCaseUseCase resolveModerationCaseUseCase) {
        this.reportContentUseCase = reportContentUseCase; this.resolveModerationCaseUseCase = resolveModerationCaseUseCase;
    }

    @PostMapping("/report")
    public ResponseEntity<ApiResponse<Map<String, UUID>>> report(@RequestParam @NotNull UUID targetId,
                                                                   @RequestParam @NotBlank String targetType,
                                                                   @RequestParam @NotBlank String reason,
                                                                   @RequestParam(required = false) String description) {
        UUID caseId = reportContentUseCase.execute(targetId, targetType, reason, description);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.created(Map.of("caseId", caseId)));
    }

    @PostMapping("/cases/{caseId}/resolve")
    public ResponseEntity<ApiResponse<Void>> resolve(@PathVariable UUID caseId,
                                                      @RequestParam @NotBlank String action,
                                                      @RequestParam(required = false) String notes) {
        resolveModerationCaseUseCase.execute(caseId, action, notes);
        return ResponseEntity.ok(ApiResponse.success(null, "Case resolved"));
    }
}
