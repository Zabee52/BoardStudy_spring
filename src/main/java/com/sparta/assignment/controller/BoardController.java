package com.sparta.assignment.controller;

import com.sparta.assignment.models.Board;
import com.sparta.assignment.dto.BoardDto;
import com.sparta.assignment.models.Comment;
import com.sparta.assignment.repository.BoardRepository;
import com.sparta.assignment.security.UserDetailsImpl;
import com.sparta.assignment.service.BoardService;
import com.sparta.assignment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class BoardController {
    private final BoardService boardService;
    private final BoardRepository boardRepository;
    private final CommentService commentService;

    @GetMapping("/board")
    public String postList(Model model) {
        List<Board> list = boardRepository.findAllByOrderByModifiedAtDesc();
        model.addAttribute("posts", list);
        return "board";
    }

    @GetMapping("/board/post/{id}")
    public String post(@PathVariable Long id, Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Board post = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("없는글임~")
        );
        List<Comment> comments = commentService.getComments(id);
        Long myId = userDetails.getUserId();
        String nickname = userDetails.getUsername();
        model.addAttribute("post", post);
        model.addAttribute("comments", comments);
        model.addAttribute("myId", myId);
        model.addAttribute("nickname", nickname);
        return "post";
    }

    @Secured("ROLE_USER")
    @PostMapping("/board")
    @ResponseBody
    public Board posting(@RequestBody BoardDto boardDto) {
        Board board = new Board(boardDto);
        return boardRepository.save(board);
    }

    @Secured("ROLE_USER")
    @PutMapping("/board/post/{id}")
    @ResponseBody
    public void modify(@PathVariable Long id, @RequestBody BoardDto boardDto) {
        boardService.update(id, boardDto);
    }

    @Secured("ROLE_USER")
    @DeleteMapping("/board/{id}")
    @ResponseBody
    public void delete(@PathVariable Long id) {
        boardRepository.deleteById(id);
    }
}