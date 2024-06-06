package week13.board.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import week13.board.domain.Post;
import week13.board.dto.PostRequestDto;
import week13.board.dto.PostUpdateRequestDto;
import week13.board.exception.CustomException;
import week13.board.exception.ErrorCode;
import week13.board.repository.BoardRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    @Transactional
    public List<Post> getAllPosts(){
        return boardRepository.findAllByOrderByCreatedAtDesc();
    }

    @Transactional
    public Post createPost(Post post) {
        return boardRepository.save(post);
    }

    @Transactional
    public Post findPostById(Long id) {
        Optional<Post> byId = boardRepository.findById(id);
        if (byId.isEmpty())
            throw new CustomException(ErrorCode.POST_NOT_FOUND);
        return byId.get();
    }

    @Transactional
    public Long updatePost(Long id, PostUpdateRequestDto requestDto) {
        Post post = boardRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));
        if(!requestDto.getPassword().equals(post.getPassword())){
            throw new CustomException(ErrorCode.PASSWORD_ERROR);
        }
        post.update(requestDto);

        return post.getId();
    }

    @Transactional
    public void deletePost(Long id) {
        boardRepository.deleteById(id);
    }
}
