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

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    @Transactional
    public CommentResponseDto createComment(Long postId, String username, CommentRequestDto requestDto) {
        Post post = boardRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException(ErrorCode.USERNAME_NOT_FOUND));

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
        Post post = boardRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

        Comment comment = commentRepository.findByIdAndPost(commentId, post)
                .orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));

        if (!comment.getUser().getUsername().equals(username)) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_USER);
        }

        comment.update(requestDto);
        Comment updatedComment = commentRepository.save(comment);
        return CommentResponseDto.of(updatedComment);
    }

    @Transactional
    public void deleteComment(Long postId, Long commentId, String username) {
        Post post = boardRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

        Comment comment = commentRepository.findByIdAndPost(commentId, post)
                .orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));

        if (!comment.getUser().getUsername().equals(username)) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_USER);
        }

        commentRepository.deleteById(commentId);
    }
}
