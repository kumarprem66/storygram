package com.storyverse.creator.adapters.persistence;

import com.storyverse.creator.domain.CreatorProfile;
import com.storyverse.creator.domain.vo.CreatorStatus;
import com.storyverse.creator.domain.vo.VerificationStatus;
import com.storyverse.creator.ports.CreatorProfileRepository;
import org.springframework.stereotype.Component;

import java.util.HashSet;
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
        Optional<CreatorProfileJpaEntity> existing = springDataRepository.findById(profile.getId());

        CreatorProfileJpaEntity entity;
        if (existing.isPresent()) {
            entity = existing.get();
            entity.setDisplayName(profile.getDisplayName());
            entity.setBiography(profile.getBiography());
            entity.setAvatarUrl(profile.getAvatarUrl());
            entity.setBannerUrl(profile.getBannerUrl());
            entity.setWebsite(profile.getWebsite());
            entity.setLanguage(profile.getLanguage());
            entity.setStatus(profile.getStatus().name());
            entity.setVerificationStatus(profile.getVerificationStatus().name());
            entity.setFollowerCount(profile.getFollowerCount());
            entity.setStoryCount(profile.getStoryCount());
            entity.setGenres(new HashSet<>(profile.getGenres()));
            entity.setPenNames(new HashSet<>(profile.getPenNames()));
        } else {
            entity = CreatorProfileMapper.toEntity(profile);
        }

        CreatorProfileJpaEntity saved = springDataRepository.save(entity);
        return CreatorProfileMapper.toDomain(saved);
    }

    @Override
    public void delete(UUID id) {
        springDataRepository.deleteById(id);
    }
}
