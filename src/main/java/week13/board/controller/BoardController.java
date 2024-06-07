package week13.board.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import week13.board.constants.ResponseMessage;
import week13.board.domain.Post;
import week13.board.dto.ApiResponse;
import week13.board.dto.PostRequestDto;
import week13.board.dto.PostResponseDto;
import week13.board.dto.PostUpdateRequestDto;
import week13.board.service.BoardService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {
    private final BoardService boardService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<PostResponseDto>>> getAllPosts() {
        List<PostResponseDto> posts = boardService.getAllPosts().stream()
                .map(PostResponseDto::of)
                .collect(Collectors.toList());
        ApiResponse<List<PostResponseDto>> response = new ApiResponse<>(HttpStatus.OK.value(), ResponseMessage.POST_LIST_SUCCESS, posts);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<PostResponseDto>> createPost(@Valid @RequestBody PostRequestDto requestDto){
        Post post = boardService.createPost(requestDto.toEntity());
        ApiResponse<PostResponseDto> response = new ApiResponse<>(HttpStatus.CREATED.value(), ResponseMessage.POST_CREATE_SUCCESS, PostResponseDto.of(post));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PostResponseDto>> getPostById(@PathVariable("id") Long id){
        Post post = boardService.findPostById(id);
        ApiResponse<PostResponseDto> response = new ApiResponse<>(HttpStatus.OK.value(), ResponseMessage.POST_GET_SUCCESS, PostResponseDto.of(post));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PostResponseDto>> updatePost(@PathVariable("id") Long id, @RequestBody PostUpdateRequestDto requestDto) {
        Post updatedPost = boardService.updatePost(id, requestDto);
        ApiResponse<PostResponseDto> response = new ApiResponse<>(HttpStatus.OK.value(), ResponseMessage.POST_UPDATE_SUCCESS, PostResponseDto.of(updatedPost));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable("id") Long id){
        boardService.deletePost(id);
        ApiResponse<Void> response = new ApiResponse<>(HttpStatus.OK.value(), ResponseMessage.POST_DELETE_SUCCESS, null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
