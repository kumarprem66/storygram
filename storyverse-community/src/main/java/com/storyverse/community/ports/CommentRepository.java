package com.storyverse.community.ports;

import com.storyverse.community.domain.Comment;
import java.util.*;

public interface CommentRepository {
    Optional<Comment> findById(UUID id);
    List<Comment> findByStoryId(UUID storyId);
    List<Comment> findReplies(UUID parentCommentId);
    Comment save(Comment comment);
    void delete(UUID id);
}
