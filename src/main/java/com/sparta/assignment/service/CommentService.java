package com.sparta.assignment.service;

import com.sparta.assignment.dto.CommentDto;
import com.sparta.assignment.dto.UpdateDto;
import com.sparta.assignment.models.Comment;
import com.sparta.assignment.repository.CommentRepository;
import com.sparta.assignment.security.UserDetailsImpl;
import com.sparta.assignment.security.ValidCheck;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    public List<Comment> getComments(Long postId) {
        return commentRepository.findAllByPostIdOrderByCreatedAtDesc(postId).orElse(null);
    }

    public String writeComment(CommentDto commentDto, UserDetailsImpl userDetails) {
        Comment comment = new Comment(commentDto);
        if(comment.getUserId() == null){
            throw new NullPointerException("로그인이 필요한 기능입니다.");
        }
        if(comment.getContent().trim().equals("")){
            throw new IllegalArgumentException("내용을 작성해주세요.");
        }
        ValidCheck.procRequestUserValidCheck(commentDto.getUserId(), userDetails);

        commentRepository.save(comment);
        return "작성 완료";
    }

    public String modifyComment(Long id, UpdateDto updateDto, UserDetailsImpl userDetails) {
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new NullPointerException("존재하지 않는 댓글입니다.")
        );

        // 요청자가 원래의 데이터 주인과 다른 사람일 경우 throw
        ValidCheck.procRequestUserValidCheck(id, userDetails);

        String newComment = updateDto.getContent();
        if(newComment.trim().equals("")){
            throw new IllegalArgumentException("내용을 작성해주세요.");
        }
        comment.setContent(newComment);
        commentRepository.save(comment);

        return "수정 완료";
    }

    public String deleteComment(Long id, UserDetailsImpl userDetails) {
        // 요청자가 원래의 데이터 주인과 다른 사람일 경우 throw
        ValidCheck.procRequestUserValidCheck(id, userDetails);

        commentRepository.deleteById(id);
        return "삭제 완료";
    }
}
