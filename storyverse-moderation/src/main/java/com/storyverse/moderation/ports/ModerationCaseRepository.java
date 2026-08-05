package com.storyverse.moderation.ports;

import com.storyverse.moderation.domain.ModerationCase;
import com.storyverse.moderation.domain.vo.ModerationStatus;
import java.util.*;

public interface ModerationCaseRepository {
    ModerationCase save(ModerationCase moderationCase);
    Optional<ModerationCase> findById(UUID id);
    List<ModerationCase> findByStatus(ModerationStatus status);
    List<ModerationCase> findByAssignedModeratorId(UUID moderatorId);
}
