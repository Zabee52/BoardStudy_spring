package com.sparta.assignment.service;

import com.sparta.assignment.dto.LikeDto;
import com.sparta.assignment.models.Board;
import com.sparta.assignment.models.Likes;
import com.sparta.assignment.repository.BoardRepository;
import com.sparta.assignment.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;
    private final BoardRepository boardRepository;

    public boolean isLike(Long postId, Long userId) {
        Optional<Likes> found = likeRepository.findByPostIdAndUserId(postId, userId);
        if(found.isPresent()){
            // DB에 좋아요 데이터가 남아있음 = 좋아요 상태라는 뜻.
            return true;
        }else{
            return false;
        }
    }
    public void likeProc(LikeDto likeDto) {
        Long postId = likeDto.getPostId();
        Long userId = likeDto.getUserId();

        Board board = boardRepository.findById(postId).orElseThrow(
                () -> new NullPointerException("유효하지 않은 게시글입니다.")
        );
        Optional<Likes> found = likeRepository.findByPostIdAndUserId(postId, userId);
        if(found.isPresent()){
            // DB에 좋아요 데이터가 있음 = 좋아요 상태라는 뜻. 안좋아요로 변환.
            likeRepository.deleteById(found.get().getId());
            board.setLikeCnt(board.getLikeCnt()-1);
        }else{
            // DB에 좋아요 데이터가 없음 = 안좋아요 상태라는 뜻. 좋아요로 변환.
            Likes like = new Likes(postId, userId);
            likeRepository.save(like);
            board.setLikeCnt(board.getLikeCnt()+1);
        }
        boardRepository.save(board);
    }
}
