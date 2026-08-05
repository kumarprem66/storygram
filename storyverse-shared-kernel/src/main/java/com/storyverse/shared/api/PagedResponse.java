package com.storyverse.shared.api;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * Standardized paginated response for all list endpoints.
 *
 * @param <T> the type of items in the page
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record PagedResponse<T>(
        List<T> content,
        PageInfo page
) {

    public static <T> PagedResponse<T> of(List<T> content, int pageNumber, int pageSize,
                                           long totalElements, int totalPages) {
        return new PagedResponse<>(content, new PageInfo(pageNumber, pageSize, totalElements, totalPages));
    }

    public static <T> PagedResponse<T> empty(int pageSize) {
        return new PagedResponse<>(List.of(), new PageInfo(0, pageSize, 0, 0));
    }

    /**
     * Pagination metadata.
     */
    public record PageInfo(
            int pageNumber,
            int pageSize,
            long totalElements,
            int totalPages
    ) {
        public boolean hasNext() {
            return pageNumber < totalPages - 1;
        }

        public boolean hasPrevious() {
            return pageNumber > 0;
        }

        public boolean isFirst() {
            return pageNumber == 0;
        }

        public boolean isLast() {
            return pageNumber >= totalPages - 1;
        }
    }
}
