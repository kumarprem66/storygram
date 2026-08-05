package com.storyverse.shared.api;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.Instant;

/**
 * Unified API response wrapper for all StoryVerse endpoints.
 * Every successful response follows this structure.
 *
 * @param <T> the type of the response payload
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiResponse<T>(
        boolean success,
        T data,
        String message,
        ResponseMetadata metadata
) {

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, data, null, ResponseMetadata.now());
    }

    public static <T> ApiResponse<T> success(T data, String message) {
        return new ApiResponse<>(true, data, message, ResponseMetadata.now());
    }

    public static <T> ApiResponse<T> created(T data) {
        return new ApiResponse<>(true, data, "Resource created successfully", ResponseMetadata.now());
    }

    public static ApiResponse<Void> noContent() {
        return new ApiResponse<>(true, null, null, ResponseMetadata.now());
    }

    /**
     * Response metadata for tracing and debugging.
     */
    public record ResponseMetadata(
            Instant timestamp,
            String correlationId
    ) {
        public static ResponseMetadata now() {
            return new ResponseMetadata(Instant.now(), null);
        }

        public static ResponseMetadata withCorrelation(String correlationId) {
            return new ResponseMetadata(Instant.now(), correlationId);
        }
    }
}
