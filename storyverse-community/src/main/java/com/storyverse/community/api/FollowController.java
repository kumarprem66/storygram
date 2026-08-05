package com.storyverse.community.api;

import com.storyverse.community.application.FollowUserUseCase;
import com.storyverse.shared.api.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/follows")
public class FollowController {
    private final FollowUserUseCase followUserUseCase;

    public FollowController(FollowUserUseCase followUserUseCase) { this.followUserUseCase = followUserUseCase; }

    @PostMapping("/{userId}")
    public ResponseEntity<ApiResponse<Map<String, UUID>>> follow(@PathVariable UUID userId) {
        UUID followId = followUserUseCase.execute(userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.created(Map.of("followId", followId)));
    }
}
