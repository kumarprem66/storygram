package com.storyverse.creator.adapters.persistence;

import com.storyverse.creator.domain.CreatorProfile;
import com.storyverse.creator.ports.CreatorProfileRepository;
import org.springframework.stereotype.Component;
import java.util.Optional;
import java.util.UUID;

@Component
public class JpaCreatorProfileRepositoryAdapter implements CreatorProfileRepository {
    private final SpringDataCreatorProfileRepository springDataRepository;

    public JpaCreatorProfileRepositoryAdapter(SpringDataCreatorProfileRepository springDataRepository) {
        this.springDataRepository = springDataRepository;
    }

    @Override
    public Optional<CreatorProfile> findById(UUID id) {
        return springDataRepository.findById(id).map(CreatorProfileMapper::toDomain);
    }

    @Override
    public Optional<CreatorProfile> findByUserId(UUID userId) {
        return springDataRepository.findByUserId(userId).map(CreatorProfileMapper::toDomain);
    }

    @Override
    public boolean existsByUserId(UUID userId) {
        return springDataRepository.existsByUserId(userId);
    }

    @Override
    public CreatorProfile save(CreatorProfile profile) {
        CreatorProfileJpaEntity entity = CreatorProfileMapper.toEntity(profile);
        CreatorProfileJpaEntity saved = springDataRepository.save(entity);
        return CreatorProfileMapper.toDomain(saved);
    }

    @Override
    public void delete(UUID id) {
        springDataRepository.deleteById(id);
    }
}
