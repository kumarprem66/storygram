package com.storyverse.administration.ports;

import com.storyverse.administration.domain.FeatureFlag;
import java.util.*;

public interface FeatureFlagRepository {
    Optional<FeatureFlag> findById(UUID id);
    Optional<FeatureFlag> findByName(String name);
    List<FeatureFlag> findAll();
    FeatureFlag save(FeatureFlag flag);
    void delete(UUID id);
}
