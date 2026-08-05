package com.storyverse.creator.adapters.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
interface SpringDataCreatorProfileRepository extends JpaRepository<CreatorProfileJpaEntity, UUID> {
    Optional<CreatorProfileJpaEntity> findByUserId(UUID userId);
    boolean existsByUserId(UUID userId);
}
