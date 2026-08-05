package com.storyverse.reader.api;

import com.storyverse.reader.application.CreateCollectionUseCase;
import com.storyverse.shared.api.ApiResponse;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/collections")
public class CollectionController {
    private final CreateCollectionUseCase createCollectionUseCase;

    public CollectionController(CreateCollectionUseCase createCollectionUseCase) {
        this.createCollectionUseCase = createCollectionUseCase;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Map<String, UUID>>> createCollection(@RequestParam @NotBlank String name,
                                                                            @RequestParam(required = false) String description,
                                                                            @RequestParam(defaultValue = "PRIVATE") String visibility) {
        UUID collectionId = createCollectionUseCase.execute(name, description, visibility);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.created(Map.of("collectionId", collectionId)));
    }
}
