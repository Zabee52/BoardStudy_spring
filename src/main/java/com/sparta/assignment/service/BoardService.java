package com.sparta.assignment.service;

import com.sparta.assignment.models.Board;
import com.sparta.assignment.dto.BoardDto;
import com.sparta.assignment.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    @Transactional
    public void update(Long id, BoardDto boardDto){
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("그런 게시물 없습니다!")
        );
        board.update(boardDto);
    }
}
