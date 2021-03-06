package com.sparta.assignment.models;

import com.sparta.assignment.dto.BoardDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Board extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private int likeCnt;

    public Board(BoardDto boardDto){
        this.title = boardDto.getTitle();
        this.author = boardDto.getAuthor();
        this.content = boardDto.getContent();
        this.likeCnt = 0;
    }

    public void update(BoardDto boardDto){
        this.title = boardDto.getTitle();
        this.author = boardDto.getAuthor();
        this.content = boardDto.getContent();
        this.likeCnt = 0;
    }

    public void setLikeCnt(int likeCnt) {
        this.likeCnt = likeCnt;
    }
}
