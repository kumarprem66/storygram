package com.storyverse.ai.domain;

import com.storyverse.ai.domain.vo.*;
import com.storyverse.shared.domain.BaseEntity;
import java.time.Instant;
import java.util.UUID;

public class AIRequest extends BaseEntity {
    private UUID userId;
    private AITaskType taskType;
    private AIProvider provider;
    private AIRequestStatus status;
    private String input;
    private String output;
    private int inputTokens;
    private int outputTokens;
    private long latencyMs;
    private String errorMessage;
    private Instant createdAt;
    private Instant completedAt;

    private AIRequest(UUID id) { super(id); }

    public static AIRequest create(UUID id, UUID userId, AITaskType taskType, String input) {
        AIRequest r = new AIRequest(id);
        r.userId = userId;
        r.taskType = taskType;
        r.status = AIRequestStatus.PENDING;
        r.input = input;
        r.createdAt = Instant.now();
        return r;
    }

    public static AIRequest reconstitute(UUID id, UUID userId, AITaskType taskType, AIProvider provider,
                                          AIRequestStatus status, String input, String output,
                                          int inputTokens, int outputTokens, long latencyMs,
                                          String errorMessage, Instant createdAt, Instant completedAt) {
        AIRequest r = new AIRequest(id);
        r.userId = userId;
        r.taskType = taskType;
        r.provider = provider;
        r.status = status;
        r.input = input;
        r.output = output;
        r.inputTokens = inputTokens;
        r.outputTokens = outputTokens;
        r.latencyMs = latencyMs;
        r.errorMessage = errorMessage;
        r.createdAt = createdAt;
        r.completedAt = completedAt;
        return r;
    }

    public void start(AIProvider provider) { this.provider = provider; this.status = AIRequestStatus.IN_PROGRESS; }
    public void complete(String output, int inputTokens, int outputTokens, long latencyMs) {
        this.output = output; this.inputTokens = inputTokens; this.outputTokens = outputTokens;
        this.latencyMs = latencyMs; this.status = AIRequestStatus.COMPLETED; this.completedAt = Instant.now();
    }
    public void fail(String errorMessage) { this.errorMessage = errorMessage; this.status = AIRequestStatus.FAILED; this.completedAt = Instant.now(); }
    public void markCached(String output) { this.output = output; this.status = AIRequestStatus.CACHED; this.completedAt = Instant.now(); }

    public UUID getUserId() { return userId; }
    public AITaskType getTaskType() { return taskType; }
    public AIProvider getProvider() { return provider; }
    public AIRequestStatus getStatus() { return status; }
    public String getInput() { return input; }
    public String getOutput() { return output; }
    public int getInputTokens() { return inputTokens; }
    public int getOutputTokens() { return outputTokens; }
    public long getLatencyMs() { return latencyMs; }
    public String getErrorMessage() { return errorMessage; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getCompletedAt() { return completedAt; }
}
