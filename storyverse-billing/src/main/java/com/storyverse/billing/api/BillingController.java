package com.storyverse.billing.api;

import com.storyverse.billing.application.*;
import com.storyverse.shared.api.ApiResponse;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/billing")
public class BillingController {
    private final SubscribeUseCase subscribeUseCase;
    private final CancelSubscriptionUseCase cancelSubscriptionUseCase;

    public BillingController(SubscribeUseCase subscribeUseCase, CancelSubscriptionUseCase cancelSubscriptionUseCase) {
        this.subscribeUseCase = subscribeUseCase;
        this.cancelSubscriptionUseCase = cancelSubscriptionUseCase;
    }

    @PostMapping("/subscribe")
    public ResponseEntity<ApiResponse<Map<String, UUID>>> subscribe(@RequestParam @NotBlank String plan) {
        UUID subscriptionId = subscribeUseCase.execute(plan);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.created(Map.of("subscriptionId", subscriptionId)));
    }

    @PostMapping("/cancel")
    public ResponseEntity<ApiResponse<Void>> cancel() {
        cancelSubscriptionUseCase.execute();
        return ResponseEntity.ok(ApiResponse.success(null, "Subscription cancelled"));
    }
}
