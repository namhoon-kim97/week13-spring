package week13.board.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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

import java.util.Collection;
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
        String username = getCurrentUsername();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException(ErrorCode.USERNAME_NOT_FOUND));
        Post newPost = new Post(post.getTitle(), post.getContents(), user);
        return boardRepository.save(newPost);
    }

    private String getCurrentUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }
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

        verifyPermission(post);
        post.update(requestDto);

        return post;
    }

    @Transactional
    public void deletePost(Long id) {
        Post post = boardRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

        verifyPermission(post);
        boardRepository.deleteById(id);
    }

    private void verifyPermission(Post post) {
        String currentUsername = getCurrentUsername();
        if (!post.getUser().getUsername().equals(currentUsername) && !isAdmin()) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_USER);
        }
    }

    // 현재 사용자가 관리자 권한을 가지고 있는지 확인하는 메서드
    private boolean isAdmin() {
        Collection<? extends GrantedAuthority> authorities =
                SecurityContextHolder.getContext().getAuthentication().getAuthorities();

        for (GrantedAuthority authority : authorities) {
            if (authority.getAuthority().equals("ADMIN")) {
                return true;
            }
        }
        return false;
    }
}
