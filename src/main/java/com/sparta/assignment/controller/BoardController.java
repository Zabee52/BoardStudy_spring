package com.sparta.assignment.controller;

import com.sparta.assignment.dto.BoardDto;
import com.sparta.assignment.models.Board;
import com.sparta.assignment.models.Comment;
import com.sparta.assignment.repository.BoardRepository;
import com.sparta.assignment.security.UserDetailsImpl;
import com.sparta.assignment.service.BoardService;
import com.sparta.assignment.service.CommentService;
import com.sparta.assignment.service.LikeService;
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
    private final LikeService likeService;

    @GetMapping("/board")
    public String postList(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long myId = Long.parseLong("-1");
        if(userDetails != null){
            myId = userDetails.getUserId();
        }

        List<Board> list = boardRepository.findAllByOrderByModifiedAtDesc();
        model.addAttribute("myId", myId);
        model.addAttribute("posts", list);
        return "board";
    }

    @GetMapping("/board/post/{id}")
    public String post(@PathVariable Long id, Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Board post = null;
        // 댓글 목록 불러오기
        List<Comment> comments = commentService.getComments(id);
        boolean isLike = false;

        try{
            post = boardService.getPost(id);
            if(userDetails != null){
                Long myId = userDetails.getUserId();
                String nickname = userDetails.getUsername();
                model.addAttribute("myId", myId);
                model.addAttribute("nickname", nickname);
                isLike = likeService.isLike(post.getId(), myId);
                model.addAttribute("isLike", isLike);
            }
        }catch(IllegalArgumentException e) {
            // 유효하지 않은 게시글일 경우 board로
            return "redirect:/board";
        }

        model.addAttribute("post", post);
        model.addAttribute("comments", comments);

        return "post";
    }

    @Secured("ROLE_USER")
    @PostMapping("/board/post")
    @ResponseBody
    public Board posting(@RequestBody BoardDto boardDto) {
        return boardService.posting(boardDto);
    }

    @Secured("ROLE_USER")
    @PutMapping("/board/post/{id}")
    @ResponseBody
    public void modify(@PathVariable Long id, @RequestBody BoardDto boardDto) {
        boardService.update(id, boardDto);
    }

    @Secured("ROLE_USER")
    @DeleteMapping("/board/post/{id}")
    @ResponseBody
    public void delete(@PathVariable Long id) {
        boardRepository.deleteById(id);
    }
}