package com.storyverse.ai.ports;

public record AIModelResponse(
        String content,
        int inputTokens,
        int outputTokens,
        long latencyMs,
        boolean success,
        String errorMessage
) {
    public static AIModelResponse success(String content, int inputTokens, int outputTokens, long latencyMs) {
        return new AIModelResponse(content, inputTokens, outputTokens, latencyMs, true, null);
    }

    public static AIModelResponse failure(String errorMessage) {
        return new AIModelResponse(null, 0, 0, 0, false, errorMessage);
    }
}
