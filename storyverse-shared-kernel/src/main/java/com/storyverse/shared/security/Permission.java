package com.storyverse.shared.security;

/**
 * Common permission constants used across StoryVerse modules.
 * Module-specific permissions should be defined in their own constants class.
 */
public final class Permission {

    private Permission() {
    }

    // Identity
    public static final String USER_READ = "user:read";
    public static final String USER_WRITE = "user:write";
    public static final String USER_DELETE = "user:delete";

    // Story
    public static final String STORY_READ = "story:read";
    public static final String STORY_WRITE = "story:write";
    public static final String STORY_PUBLISH = "story:publish";
    public static final String STORY_DELETE = "story:delete";

    // Community
    public static final String COMMENT_WRITE = "comment:write";
    public static final String COMMENT_DELETE = "comment:delete";
    public static final String REVIEW_WRITE = "review:write";

    // Moderation
    public static final String MODERATION_READ = "moderation:read";
    public static final String MODERATION_ACTION = "moderation:action";

    // Administration
    public static final String ADMIN_READ = "admin:read";
    public static final String ADMIN_WRITE = "admin:write";
    public static final String ADMIN_CONFIG = "admin:config";
}
