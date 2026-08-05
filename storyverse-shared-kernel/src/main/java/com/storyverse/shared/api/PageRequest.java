package com.storyverse.shared.api;

/**
 * Pagination request parameters.
 * Used by API controllers to accept pagination input from clients.
 */
public record PageRequest(
        int page,
        int size,
        String sortBy,
        SortDirection sortDirection
) {

    public static final int DEFAULT_PAGE = 0;
    public static final int DEFAULT_SIZE = 20;
    public static final int MAX_SIZE = 100;

    public PageRequest {
        if (page < 0) page = DEFAULT_PAGE;
        if (size <= 0) size = DEFAULT_SIZE;
        if (size > MAX_SIZE) size = MAX_SIZE;
        if (sortDirection == null) sortDirection = SortDirection.ASC;
    }

    public static PageRequest of(int page, int size) {
        return new PageRequest(page, size, null, SortDirection.ASC);
    }

    public static PageRequest of(int page, int size, String sortBy, SortDirection direction) {
        return new PageRequest(page, size, sortBy, direction);
    }

    public static PageRequest defaultPage() {
        return new PageRequest(DEFAULT_PAGE, DEFAULT_SIZE, null, SortDirection.ASC);
    }

    /**
     * Returns the offset for SQL/JPA queries.
     */
    public long offset() {
        return (long) page * size;
    }
}
