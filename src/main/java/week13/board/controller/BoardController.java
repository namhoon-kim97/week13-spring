package week13.board.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import week13.board.constants.ResponseMessage;
import week13.board.domain.Post;
import week13.board.dto.*;
import week13.board.service.BoardService;
import week13.board.util.CommentUtils;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {
    private final BoardService boardService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<PostResponseDto>>> getAllPosts() {
        ApiResponse<List<PostResponseDto>> response = new ApiResponse<>(HttpStatus.OK.value(), ResponseMessage.POST_LIST_SUCCESS, boardService.getAllPosts());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<PostResponseDto>> createPost(@Valid @RequestBody PostRequestDto requestDto) {
        Post post = boardService.createPost(requestDto.toEntity());

        List<CommentResponseDto> comments = CommentUtils.convertAndSortComments(post.getComments());

        PostResponseDto postResponseDto = PostResponseDto.of(post, comments);
        ApiResponse<PostResponseDto> response = new ApiResponse<>(HttpStatus.CREATED.value(), ResponseMessage.POST_CREATE_SUCCESS, postResponseDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PostResponseDto>> getPostById(@PathVariable("id") Long id){
        ApiResponse<PostResponseDto> response = new ApiResponse<>(HttpStatus.OK.value(), ResponseMessage.POST_GET_SUCCESS, boardService.findPostById(id));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PostResponseDto>> updatePost(@PathVariable("id") Long id, @RequestBody PostUpdateRequestDto requestDto) {
        Post updatedPost = boardService.updatePost(id, requestDto);

        List<CommentResponseDto> comments = CommentUtils.convertAndSortComments(updatedPost.getComments());

        ApiResponse<PostResponseDto> response = new ApiResponse<>(HttpStatus.OK.value(), ResponseMessage.POST_UPDATE_SUCCESS, PostResponseDto.of(updatedPost, comments));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable("id") Long id){
        boardService.deletePost(id);
        ApiResponse<Void> response = new ApiResponse<>(HttpStatus.OK.value(), ResponseMessage.POST_DELETE_SUCCESS, null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
