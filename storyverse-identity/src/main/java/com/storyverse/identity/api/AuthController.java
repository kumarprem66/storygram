package com.storyverse.identity.api;

import com.storyverse.identity.api.dto.*;
import com.storyverse.identity.application.*;
import com.storyverse.shared.api.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * REST controller for authentication endpoints.
 * Handles registration, login, token refresh, email verification, and logout.
 */
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final RegisterUserUseCase registerUserUseCase;
    private final LoginUserUseCase loginUserUseCase;
    private final RefreshTokenUseCase refreshTokenUseCase;
    private final VerifyEmailUseCase verifyEmailUseCase;
    private final LogoutUserUseCase logoutUserUseCase;

    public AuthController(RegisterUserUseCase registerUserUseCase,
                          LoginUserUseCase loginUserUseCase,
                          RefreshTokenUseCase refreshTokenUseCase,
                          VerifyEmailUseCase verifyEmailUseCase,
                          LogoutUserUseCase logoutUserUseCase) {
        this.registerUserUseCase = registerUserUseCase;
        this.loginUserUseCase = loginUserUseCase;
        this.refreshTokenUseCase = refreshTokenUseCase;
        this.verifyEmailUseCase = verifyEmailUseCase;
        this.logoutUserUseCase = logoutUserUseCase;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserRegisteredResponse>> register(@Valid @RequestBody RegisterRequest request) {
        UUID userId = registerUserUseCase.execute(
                request.email(), request.username(), request.password(), request.displayName()
        );
        UserRegisteredResponse response = new UserRegisteredResponse(userId, "Registration successful. Please verify your email.");
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.created(response));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<TokenResponse>> login(@Valid @RequestBody LoginRequest request) {
        AuthTokenResponse tokens = loginUserUseCase.execute(request.email(), request.password());
        TokenResponse response = new TokenResponse(tokens.accessToken(), tokens.refreshToken());
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<TokenResponse>> refresh(@Valid @RequestBody RefreshTokenRequest request) {
        AuthTokenResponse tokens = refreshTokenUseCase.execute(request.refreshToken());
        TokenResponse response = new TokenResponse(tokens.accessToken(), tokens.refreshToken());
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PostMapping("/verify-email")
    public ResponseEntity<ApiResponse<Void>> verifyEmail(@RequestParam String token) {
        verifyEmailUseCase.execute(token);
        return ResponseEntity.ok(ApiResponse.success(null, "Email verified successfully"));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(@Valid @RequestBody RefreshTokenRequest request) {
        logoutUserUseCase.execute(request.refreshToken());
        return ResponseEntity.ok(ApiResponse.noContent());
    }
}
