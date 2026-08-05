package com.storyverse.community.ports;

import com.storyverse.community.domain.Rating;
import java.util.*;

public interface RatingRepository {
    Optional<Rating> findByUserIdAndStoryId(UUID userId, UUID storyId);
    List<Rating> findByStoryId(UUID storyId);
    double getAverageRatingByStoryId(UUID storyId);
    Rating save(Rating rating);
    void delete(UUID id);
}
