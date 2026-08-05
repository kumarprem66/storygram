package com.storyverse.ai.application;

import com.storyverse.ai.domain.vo.AITaskType;
import org.springframework.stereotype.Service;

@Service
public class AIWriterUseCase {
    private final AIService aiService;

    public AIWriterUseCase(AIService aiService) { this.aiService = aiService; }

    public String continueWriting(String context) { return aiService.execute(AITaskType.CONTINUE_WRITING, context, 500); }
    public String rewrite(String text) { return aiService.execute(AITaskType.REWRITE, text, 500); }
    public String expand(String text) { return aiService.execute(AITaskType.EXPAND, text, 1000); }
    public String summarize(String text) { return aiService.execute(AITaskType.SUMMARIZE, text, 300); }
    public String improveGrammar(String text) { return aiService.execute(AITaskType.IMPROVE_GRAMMAR, text, 500); }
    public String generateTitle(String storyDescription) { return aiService.execute(AITaskType.GENERATE_TITLE, storyDescription, 50); }
    public String generateTags(String storyContent) { return aiService.execute(AITaskType.GENERATE_TAGS, storyContent, 100); }
}
