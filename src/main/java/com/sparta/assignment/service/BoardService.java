package com.sparta.assignment.service;

import com.sparta.assignment.models.Board;
import com.sparta.assignment.dto.BoardDto;
import com.sparta.assignment.repository.BoardRepository;
import com.sparta.assignment.security.UserDetailsImpl;
import com.sparta.assignment.security.ValidCheck;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    public Board posting(BoardDto boardDto) {
        boardDto = boardDtoCleanXss(boardDto);

        Board board = new Board(boardDto);
        return boardRepository.save(board);
    }

    @Transactional
    public void update(Long id, BoardDto boardDto){
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("그런 게시물 없습니다!")
        );

        boardDto = boardDtoCleanXss(boardDto);
        board.update(boardDto);
    }


    // cleanXss 로직 적용
    private BoardDto boardDtoCleanXss(BoardDto boardDto){
        boardDto.setTitle(ValidCheck.cleanXSS(boardDto.getTitle()));
        boardDto.setContent(ValidCheck.cleanXSS(boardDto.getContent()));
        boardDto.setAuthor(ValidCheck.cleanXSS(boardDto.getAuthor()));

        return boardDto;
    }
}
