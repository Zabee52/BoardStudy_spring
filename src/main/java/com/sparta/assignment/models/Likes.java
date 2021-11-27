package com.sparta.assignment.models;

import com.sparta.assignment.dto.LikeDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Likes {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private Long postId;

    @Column(nullable = false)
    private Long userId;

    public Likes(Long postId, Long userId) {
        this.postId = postId;
        this.userId = userId;
    }

    public Likes(LikeDto likeDto) {
        this.postId = likeDto.getPostId();
        this.userId = likeDto.getUserId();
    }
}