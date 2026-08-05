package com.storyverse.story.application;

import com.storyverse.shared.domain.exception.ResourceNotFoundException;
import com.storyverse.story.domain.Story;
import com.storyverse.story.ports.StoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class GetStoryUseCase {

    private final StoryRepository storyRepository;

    public GetStoryUseCase(StoryRepository storyRepository) {
        this.storyRepository = storyRepository;
    }

    @Transactional(readOnly = true)
    public Story execute(UUID storyId) {
        return storyRepository.findById(storyId)
                .orElseThrow(() -> new ResourceNotFoundException("Story", storyId));
    }
}
