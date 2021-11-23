package com.sparta.assignment.repository;

import com.sparta.assignment.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<List<Comment>> findAllByPostIdOrderByCreatedAtDesc(Long postId);
}
