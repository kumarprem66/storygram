package com.storyverse.administration.api;

import com.storyverse.administration.application.*;
import com.storyverse.administration.domain.FeatureFlag;
import com.storyverse.shared.api.ApiResponse;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {
    private final ManageFeatureFlagUseCase featureFlagUseCase;
    private final ManagePlatformConfigUseCase configUseCase;

    public AdminController(ManageFeatureFlagUseCase featureFlagUseCase, ManagePlatformConfigUseCase configUseCase) {
        this.featureFlagUseCase = featureFlagUseCase; this.configUseCase = configUseCase;
    }

    @GetMapping("/feature-flags")
    public ResponseEntity<ApiResponse<List<FeatureFlag>>> listFlags() {
        return ResponseEntity.ok(ApiResponse.success(featureFlagUseCase.listAll()));
    }

    @PostMapping("/feature-flags")
    public ResponseEntity<ApiResponse<Map<String, UUID>>> createFlag(@RequestParam @NotBlank String name, @RequestParam(required = false) String description) {
        UUID id = featureFlagUseCase.create(name, description);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.created(Map.of("flagId", id)));
    }

    @PostMapping("/feature-flags/{flagId}/enable")
    public ResponseEntity<ApiResponse<Void>> enableFlag(@PathVariable UUID flagId) {
        featureFlagUseCase.enable(flagId);
        return ResponseEntity.ok(ApiResponse.success(null, "Feature flag enabled"));
    }

    @PostMapping("/feature-flags/{flagId}/disable")
    public ResponseEntity<ApiResponse<Void>> disableFlag(@PathVariable UUID flagId) {
        featureFlagUseCase.disable(flagId);
        return ResponseEntity.ok(ApiResponse.success(null, "Feature flag disabled"));
    }

    @GetMapping("/config/{key}")
    public ResponseEntity<ApiResponse<Map<String, String>>> getConfig(@PathVariable String key) {
        String value = configUseCase.getValue(key);
        return ResponseEntity.ok(ApiResponse.success(Map.of("key", key, "value", value)));
    }

    @PutMapping("/config/{key}")
    public ResponseEntity<ApiResponse<Void>> setConfig(@PathVariable String key, @RequestParam @NotBlank String value, @RequestParam(required = false) String description) {
        configUseCase.setValue(key, value, description);
        return ResponseEntity.ok(ApiResponse.success(null, "Configuration updated"));
    }
}
