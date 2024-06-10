package week13.board.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import week13.board.constants.ResponseMessage;
import week13.board.dto.ApiResponse;
import week13.board.dto.CommentRequestDto;
import week13.board.dto.CommentResponseDto;
import week13.board.service.CommentService;

@RestController
@RequestMapping("/comments/{postId}")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<ApiResponse<CommentResponseDto>> createComment(@PathVariable Long postId, @RequestBody CommentRequestDto requestDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        CommentResponseDto commentResponseDto = commentService.createComment(postId, currentUsername, requestDto);
        ApiResponse<CommentResponseDto> response = new ApiResponse<>(HttpStatus.CREATED.value(), ResponseMessage.COMMENT_CREATE_SUCCESS, commentResponseDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<ApiResponse<CommentResponseDto>> updateComment(@PathVariable Long postId, @PathVariable Long commentId, @RequestBody CommentRequestDto requestDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        CommentResponseDto updatedCommentResponseDto = commentService.updateComment(postId, commentId, currentUsername, requestDto);
        ApiResponse<CommentResponseDto> response = new ApiResponse<>(HttpStatus.OK.value(), ResponseMessage.COMMENT_UPDATE_SUCCESS, updatedCommentResponseDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<ApiResponse<Void>> deleteComment(@PathVariable Long postId, @PathVariable Long commentId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        commentService.deleteComment(postId, commentId, currentUsername);
        ApiResponse<Void> response = new ApiResponse<>(HttpStatus.OK.value(), ResponseMessage.COMMENT_DELETE_SUCCESS, null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

