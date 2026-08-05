package com.storyverse.shared.domain.exception;

import java.util.UUID;

/**
 * Thrown when a requested resource does not exist.
 */
public class ResourceNotFoundException extends StoryVerseException {

    private final String resourceType;
    private final String resourceId;

    public ResourceNotFoundException(String resourceType, String resourceId) {
        super("RESOURCE_NOT_FOUND", resourceType + " not found with ID: " + resourceId);
        this.resourceType = resourceType;
        this.resourceId = resourceId;
    }

    public ResourceNotFoundException(String resourceType, UUID resourceId) {
        this(resourceType, resourceId.toString());
    }

    public String getResourceType() {
        return resourceType;
    }

    public String getResourceId() {
        return resourceId;
    }
}
