package com.storyverse.search.domain.event;

import com.storyverse.shared.domain.event.BaseDomainEvent;
import com.storyverse.shared.domain.event.EventMetadata;
import java.util.UUID;

public class SearchIndexedEvent extends BaseDomainEvent {
    private final String documentType;

    public SearchIndexedEvent(UUID documentId, String documentType, EventMetadata metadata) {
        super(documentId, metadata);
        this.documentType = documentType;
    }

    @Override public String getEventType() { return "SearchIndexed"; }
    @Override public String getAggregateType() { return "SearchIndex"; }
    public String getDocumentType() { return documentType; }
}
