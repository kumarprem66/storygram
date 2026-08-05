package com.storyverse.creator.adapters.persistence;

import com.storyverse.creator.domain.CreatorProfile;
import com.storyverse.creator.domain.vo.*;
import java.util.HashSet;

final class CreatorProfileMapper {
    private CreatorProfileMapper() {}

    static CreatorProfile toDomain(CreatorProfileJpaEntity e) {
        return CreatorProfile.reconstitute(e.getId(), e.getUserId(), e.getDisplayName(), e.getBiography(),
                e.getAvatarUrl(), e.getBannerUrl(), e.getWebsite(),
                new HashSet<>(), // socialLinks stored separately in future
                e.getGenres() != null ? new HashSet<>(e.getGenres()) : new HashSet<>(),
                e.getPenNames() != null ? new HashSet<>(e.getPenNames()) : new HashSet<>(),
                e.getLanguage(),
                CreatorStatus.valueOf(e.getStatus()),
                VerificationStatus.valueOf(e.getVerificationStatus()),
                e.getFollowerCount(), e.getStoryCount(), e.getCreatedAt(), e.getUpdatedAt());
    }

    static CreatorProfileJpaEntity toEntity(CreatorProfile p) {
        CreatorProfileJpaEntity e = new CreatorProfileJpaEntity();
        e.setId(p.getId());
        e.setUserId(p.getUserId());
        e.setDisplayName(p.getDisplayName());
        e.setBiography(p.getBiography());
        e.setAvatarUrl(p.getAvatarUrl());
        e.setBannerUrl(p.getBannerUrl());
        e.setWebsite(p.getWebsite());
        e.setLanguage(p.getLanguage());
        e.setStatus(p.getStatus().name());
        e.setVerificationStatus(p.getVerificationStatus().name());
        e.setFollowerCount(p.getFollowerCount());
        e.setStoryCount(p.getStoryCount());
        e.setGenres(new HashSet<>(p.getGenres()));
        e.setPenNames(new HashSet<>(p.getPenNames()));
        return e;
    }
}
