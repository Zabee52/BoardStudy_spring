package com.sparta.assignment.models;

import com.sparta.assignment.dto.CommentDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class Comment extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private Long postId;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String content;


    public Comment(CommentDto commentDto) {
        this.postId = commentDto.getPostId();
        this.userId = commentDto.getUserId();
        this.nickname = commentDto.getNickname();
        this.content = commentDto.getContent();
    }

    public void setContent(String content){
        this.content = content;
    }
}
