package com.storyverse.analytics.domain;

public record DashboardData(
        long totalUsers,
        long totalStories,
        long totalReads,
        long totalComments,
        long activeUsersToday,
        long storiesPublishedToday,
        long aiRequestsToday
) {}
