package week13.board.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import week13.board.domain.Post;
import week13.board.domain.User;
import week13.board.dto.CommentResponseDto;
import week13.board.dto.PostResponseDto;
import week13.board.dto.PostUpdateRequestDto;
import week13.board.exception.CustomException;
import week13.board.exception.ErrorCode;
import week13.board.repository.BoardRepository;
import week13.board.repository.UserRepository;
import week13.board.util.CommentUtils;
import week13.board.util.SecurityUtil;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    @Transactional
    public List<PostResponseDto> getAllPosts() {
        List<Post> posts = boardRepository.findAllByOrderByCreatedAtDesc();

        return posts.stream()
                .map(post -> PostResponseDto.of(post, CommentUtils.convertAndSortComments(post.getComments())))
                .collect(Collectors.toList());
    }

    @Transactional
    public Post createPost(Post post) {
        String username = SecurityUtil.getCurrentUsername();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException(ErrorCode.USERNAME_NOT_FOUND));
        Post newPost = new Post(post.getTitle(), post.getContents(), user);
        return boardRepository.save(newPost);
    }

    @Transactional
    public PostResponseDto findPostById(Long id) {
        Post post = boardRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

        List<CommentResponseDto> comments = CommentUtils.convertAndSortComments(post.getComments());

        return PostResponseDto.of(post, comments);
    }

    @Transactional
    public Post updatePost(Long id, PostUpdateRequestDto requestDto) {
        Post post = boardRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

        SecurityUtil.verifyPermission(post);
        post.update(requestDto);

        return post;
    }

    @Transactional
    public void deletePost(Long id) {
        Post post = boardRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

        SecurityUtil.verifyPermission(post);
        boardRepository.deleteById(id);
    }
}
