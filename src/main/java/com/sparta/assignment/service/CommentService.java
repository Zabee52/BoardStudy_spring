package com.sparta.assignment.service;

import com.sparta.assignment.dto.CommentDto;
import com.sparta.assignment.dto.UpdateDto;
import com.sparta.assignment.models.Comment;
import com.sparta.assignment.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    public List<Comment> getComments(Long postId) {
        return commentRepository.findAllByPostIdOrderByCreatedAtDesc(postId).orElse(null);
    }

    public Comment writeComment(CommentDto commentDto) {
        Comment comment = new Comment(commentDto);
        return commentRepository.save(comment);
    }

    public void modifyComment(Long id, UpdateDto updateDto) {
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new NullPointerException("존재하지 않는 댓글입니다.")
        );

        comment.setContent(updateDto.getContent());
        commentRepository.save(comment);
    }

    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }
}
