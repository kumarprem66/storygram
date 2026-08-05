package com.storyverse.story.domain.vo;

import com.storyverse.shared.domain.BaseValueObject;

/**
 * Word count value object with reading time estimation.
 */
public final class WordCount extends BaseValueObject {

    private static final int AVERAGE_READING_WPM = 250;

    private final int value;

    private WordCount(int value) {
        this.value = value;
    }

    public static WordCount of(int value) {
        if (value < 0) {
            throw new IllegalArgumentException("Word count cannot be negative");
        }
        return new WordCount(value);
    }

    public static WordCount zero() {
        return new WordCount(0);
    }

    public static WordCount fromText(String text) {
        if (text == null || text.isBlank()) {
            return zero();
        }
        int count = text.trim().split("\\s+").length;
        return new WordCount(count);
    }

    public int getValue() {
        return value;
    }

    /**
     * Estimated reading time in minutes.
     */
    public int estimatedReadingTimeMinutes() {
        return Math.max(1, (int) Math.ceil((double) value / AVERAGE_READING_WPM));
    }

    public WordCount add(WordCount other) {
        return new WordCount(this.value + other.value);
    }

    public WordCount subtract(WordCount other) {
        return new WordCount(Math.max(0, this.value - other.value));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WordCount wordCount = (WordCount) o;
        return value == wordCount.value;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(value);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
