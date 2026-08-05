package com.storyverse.shared.api;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.Instant;
import java.util.List;
import java.util.Map;

/**
 * Standardized error response for all StoryVerse API errors.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponse(
        boolean success,
        String errorCode,
        String message,
        String path,
        Map<String, List<String>> fieldErrors,
        ErrorMetadata metadata
) {

    public static ErrorResponse of(String errorCode, String message, String path) {
        return new ErrorResponse(false, errorCode, message, path, null, ErrorMetadata.now());
    }

    public static ErrorResponse withFields(String errorCode, String message, String path,
                                           Map<String, List<String>> fieldErrors) {
        return new ErrorResponse(false, errorCode, message, path, fieldErrors, ErrorMetadata.now());
    }

    public record ErrorMetadata(
            Instant timestamp,
            String correlationId
    ) {
        public static ErrorMetadata now() {
            return new ErrorMetadata(Instant.now(), null);
        }

        public static ErrorMetadata withCorrelation(String correlationId) {
            return new ErrorMetadata(Instant.now(), correlationId);
        }
    }
}
