package com.storyverse.administration.application;

import com.storyverse.administration.domain.PlatformConfig;
import com.storyverse.administration.ports.PlatformConfigRepository;
import com.storyverse.shared.domain.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;

@Service
public class ManagePlatformConfigUseCase {
    private final PlatformConfigRepository repository;

    public ManagePlatformConfigUseCase(PlatformConfigRepository repository) { this.repository = repository; }

    public List<PlatformConfig> listAll() { return repository.findAll(); }

    public String getValue(String key) {
        return repository.findByKey(key).map(PlatformConfig::getValue)
                .orElseThrow(() -> new ResourceNotFoundException("PlatformConfig", key));
    }

    @Transactional
    public void setValue(String key, String value, String description) {
        PlatformConfig config = repository.findByKey(key)
                .orElse(PlatformConfig.create(UUID.randomUUID(), key, value, description));
        config.updateValue(value);
        repository.save(config);
    }
}
