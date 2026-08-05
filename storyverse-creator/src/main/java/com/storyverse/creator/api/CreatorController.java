package com.storyverse.creator.api;

import com.storyverse.creator.api.dto.*;
import com.storyverse.creator.application.*;
import com.storyverse.creator.domain.CreatorProfile;
import com.storyverse.shared.api.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/creators")
public class CreatorController {
    private final BecomeCreatorUseCase becomeCreatorUseCase;
    private final UpdateCreatorProfileUseCase updateCreatorProfileUseCase;
    private final GetCreatorProfileUseCase getCreatorProfileUseCase;

    public CreatorController(BecomeCreatorUseCase becomeCreatorUseCase,
                             UpdateCreatorProfileUseCase updateCreatorProfileUseCase,
                             GetCreatorProfileUseCase getCreatorProfileUseCase) {
        this.becomeCreatorUseCase = becomeCreatorUseCase;
        this.updateCreatorProfileUseCase = updateCreatorProfileUseCase;
        this.getCreatorProfileUseCase = getCreatorProfileUseCase;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Map<String, UUID>>> becomeCreator(@Valid @RequestBody BecomeCreatorRequest request) {
        UUID creatorId = becomeCreatorUseCase.execute(request.displayName());
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.created(Map.of("creatorId", creatorId)));
    }

    @GetMapping("/{creatorId}")
    public ResponseEntity<ApiResponse<CreatorProfileResponse>> getCreator(@PathVariable UUID creatorId) {
        CreatorProfile profile = getCreatorProfileUseCase.execute(creatorId);
        return ResponseEntity.ok(ApiResponse.success(toResponse(profile)));
    }

    @PutMapping("/{creatorId}")
    public ResponseEntity<ApiResponse<Void>> updateProfile(@PathVariable UUID creatorId,
                                                            @Valid @RequestBody UpdateCreatorProfileRequest request) {
        updateCreatorProfileUseCase.execute(creatorId, request.displayName(), request.biography(),
                request.website(), request.language(), request.genres(), request.penNames());
        return ResponseEntity.ok(ApiResponse.success(null, "Creator profile updated"));
    }

    private CreatorProfileResponse toResponse(CreatorProfile p) {
        return new CreatorProfileResponse(p.getId(), p.getUserId(), p.getDisplayName(), p.getBiography(),
                p.getAvatarUrl(), p.getBannerUrl(), p.getWebsite(), p.getGenres(), p.getPenNames(),
                p.getLanguage(), p.getStatus().name(), p.getVerificationStatus().name(),
                p.getFollowerCount(), p.getStoryCount(), p.getCreatedAt(), p.getUpdatedAt());
    }
}
