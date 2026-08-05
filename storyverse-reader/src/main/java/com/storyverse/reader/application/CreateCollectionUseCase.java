package com.storyverse.reader.application;

import com.storyverse.reader.domain.ReadingCollection;
import com.storyverse.reader.domain.vo.CollectionVisibility;
import com.storyverse.reader.ports.ReadingCollectionRepository;
import com.storyverse.shared.security.SecurityContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@Service
public class CreateCollectionUseCase {
    private final ReadingCollectionRepository repository;
    private final SecurityContext securityContext;

    public CreateCollectionUseCase(ReadingCollectionRepository repository, SecurityContext securityContext) {
        this.repository = repository;
        this.securityContext = securityContext;
    }

    @Transactional
    public UUID execute(String name, String description, String visibility) {
        UUID userId = securityContext.requireUserId();
        CollectionVisibility vis = CollectionVisibility.valueOf(visibility.toUpperCase());
        ReadingCollection collection = ReadingCollection.create(UUID.randomUUID(), userId, name, description, vis);
        repository.save(collection);
        return collection.getId();
    }
}
