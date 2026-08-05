package com.storyverse.ai.ports;

import com.storyverse.ai.domain.vo.AITaskType;

/**
 * Port for AI model invocation. Implemented by provider adapters (OpenAI, Gemini, etc.).
 * Business modules never call this directly - they use AIService.
 */
public interface AIModelPort {
    AIModelResponse invoke(String prompt, AITaskType taskType, int maxTokens);
}
