package com.storyverse.community.adapters;

import com.storyverse.community.domain.Comment;
import com.storyverse.community.ports.CommentRepository;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class InMemoryCommentRepository implements CommentRepository {
    private final Map<UUID, Comment> store = new ConcurrentHashMap<>();

    @Override public Optional<Comment> findById(UUID id) { return Optional.ofNullable(store.get(id)); }
    @Override public List<Comment> findByStoryId(UUID storyId) { return store.values().stream().filter(c -> c.getStoryId().equals(storyId)).toList(); }
    @Override public List<Comment> findReplies(UUID parentCommentId) { return store.values().stream().filter(c -> parentCommentId.equals(c.getParentCommentId())).toList(); }
    @Override public Comment save(Comment comment) { store.put(comment.getId(), comment); return comment; }
    @Override public void delete(UUID id) { store.remove(id); }
}
