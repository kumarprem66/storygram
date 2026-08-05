package com.storyverse.ai.application;

import com.storyverse.ai.domain.vo.AITaskType;
import org.springframework.stereotype.Service;

@Service
public class AIReaderUseCase {
    private final AIService aiService;

    public AIReaderUseCase(AIService aiService) { this.aiService = aiService; }

    public String summarizeStory(String storyContent) { return aiService.execute(AITaskType.STORY_SUMMARY, storyContent, 300); }
    public String summarizeChapter(String chapterContent) { return aiService.execute(AITaskType.CHAPTER_SUMMARY, chapterContent, 200); }
    public String explainText(String text) { return aiService.execute(AITaskType.EXPLAIN_TEXT, text, 300); }
    public String vocabularyHelp(String word) { return aiService.execute(AITaskType.VOCABULARY_HELP, word, 200); }
}
