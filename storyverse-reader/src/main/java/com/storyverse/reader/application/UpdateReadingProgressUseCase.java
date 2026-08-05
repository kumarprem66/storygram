package com.storyverse.reader.application;

import com.storyverse.reader.domain.ReadingProgress;
import com.storyverse.reader.ports.ReadingProgressRepository;
import com.storyverse.shared.domain.exception.ResourceNotFoundException;
import com.storyverse.shared.security.SecurityContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@Service
public class UpdateReadingProgressUseCase {
    private final ReadingProgressRepository repository;
    private final SecurityContext securityContext;

    public UpdateReadingProgressUseCase(ReadingProgressRepository repository, SecurityContext securityContext) {
        this.repository = repository;
        this.securityContext = securityContext;
    }

    @Transactional
    public void execute(UUID storyId, UUID chapterId, int chapterIndex, double percentage, long sessionSeconds) {
        UUID userId = securityContext.requireUserId();
        ReadingProgress progress = repository.findByUserIdAndStoryId(userId, storyId)
                .orElseThrow(() -> new ResourceNotFoundException("ReadingProgress", storyId));
        progress.updateProgress(chapterId, chapterIndex, percentage, sessionSeconds);
        repository.save(progress);
    }
}
