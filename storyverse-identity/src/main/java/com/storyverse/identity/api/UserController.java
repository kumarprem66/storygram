package com.storyverse.identity.api;

import com.storyverse.identity.api.dto.ChangePasswordRequest;
import com.storyverse.identity.application.ChangePasswordUseCase;
import com.storyverse.shared.api.ApiResponse;
import com.storyverse.shared.security.SecurityContext;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * REST controller for authenticated user operations.
 */
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final ChangePasswordUseCase changePasswordUseCase;
    private final SecurityContext securityContext;

    public UserController(ChangePasswordUseCase changePasswordUseCase,
                          SecurityContext securityContext) {
        this.changePasswordUseCase = changePasswordUseCase;
        this.securityContext = securityContext;
    }

    @PutMapping("/me/password")
    public ResponseEntity<ApiResponse<Void>> changePassword(@Valid @RequestBody ChangePasswordRequest request) {
        UUID userId = securityContext.requireUserId();
        changePasswordUseCase.execute(userId, request.currentPassword(), request.newPassword());
        return ResponseEntity.ok(ApiResponse.success(null, "Password changed successfully"));
    }
}
