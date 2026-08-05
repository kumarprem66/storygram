package com.storyverse.shared.domain;

import com.storyverse.shared.domain.event.DomainEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Base class for aggregate roots.
 * Aggregates collect domain events that are published after persistence.
 */
public abstract class BaseAggregateRoot extends BaseEntity {

    private final List<DomainEvent> domainEvents = new ArrayList<>();

    protected BaseAggregateRoot(UUID id) {
        super(id);
    }

    /**
     * Register a domain event to be published after the aggregate is persisted.
     */
    protected void registerEvent(DomainEvent event) {
        domainEvents.add(event);
    }

    /**
     * Returns all registered domain events (unmodifiable).
     */
    public List<DomainEvent> getDomainEvents() {
        return Collections.unmodifiableList(domainEvents);
    }

    /**
     * Clears all registered domain events. Called after events are published.
     */
    public void clearDomainEvents() {
        domainEvents.clear();
    }
}
