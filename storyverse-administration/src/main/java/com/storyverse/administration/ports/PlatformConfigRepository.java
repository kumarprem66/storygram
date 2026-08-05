package com.storyverse.administration.ports;

import com.storyverse.administration.domain.PlatformConfig;
import java.util.*;

public interface PlatformConfigRepository {
    Optional<PlatformConfig> findByKey(String key);
    List<PlatformConfig> findAll();
    PlatformConfig save(PlatformConfig config);
}
