package com.storyverse.creator.domain.vo;

import com.storyverse.shared.domain.BaseValueObject;

public final class SocialLink extends BaseValueObject {

    private final String platform;
    private final String url;

    private SocialLink(String platform, String url) {
        this.platform = platform;
        this.url = url;
    }

    public static SocialLink of(String platform, String url) {
        if (platform == null || platform.isBlank()) throw new IllegalArgumentException("Platform must not be blank");
        if (url == null || url.isBlank()) throw new IllegalArgumentException("URL must not be blank");
        return new SocialLink(platform.toLowerCase().trim(), url.trim());
    }

    public String getPlatform() { return platform; }
    public String getUrl() { return url; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SocialLink that = (SocialLink) o;
        return platform.equals(that.platform) && url.equals(that.url);
    }

    @Override
    public int hashCode() { return 31 * platform.hashCode() + url.hashCode(); }

    @Override
    public String toString() { return platform + ":" + url; }
}
