package week13.board.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import week13.board.domain.Comment;
import week13.board.domain.Post;
import week13.board.domain.User;
import week13.board.dto.CommentRequestDto;
import week13.board.dto.CommentResponseDto;
import week13.board.exception.CustomException;
import week13.board.exception.ErrorCode;
import week13.board.repository.BoardRepository;
import week13.board.repository.CommentRepository;
import week13.board.repository.UserRepository;
import week13.board.util.SecurityUtil;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    @Transactional
    public CommentResponseDto createComment(Long postId, String username, CommentRequestDto requestDto) {
        Post post = findPostById(postId);
        User user = findUserByUsername(username);

        Comment comment = Comment.builder()
                .post(post)
                .user(user)
                .content(requestDto.getComment())
                .build();

        Comment savedComment = commentRepository.save(comment);
        return CommentResponseDto.of(savedComment);
    }

    @Transactional
    public CommentResponseDto updateComment(Long postId, Long commentId, String username, CommentRequestDto requestDto) {
        Post post = findPostById(postId);
        Comment comment = findCommentByIdAndPost(commentId, post);

        SecurityUtil.verifyCommentPermission(comment);

        comment.update(requestDto);
        Comment updatedComment = commentRepository.save(comment);
        return CommentResponseDto.of(updatedComment);
    }

    @Transactional
    public void deleteComment(Long postId, Long commentId, String username) {
        Post post = findPostById(postId);
        Comment comment = findCommentByIdAndPost(commentId, post);

        SecurityUtil.verifyCommentPermission(comment);

        commentRepository.deleteById(commentId);
    }

    private Post findPostById(Long postId) {
        return boardRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));
    }

    private User findUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException(ErrorCode.USERNAME_NOT_FOUND));
    }

    private Comment findCommentByIdAndPost(Long commentId, Post post) {
        return commentRepository.findByIdAndPost(commentId, post)
                .orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));
    }
}
