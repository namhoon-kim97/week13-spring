package week13.board.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import week13.board.dto.CommentRequestDto;
import week13.board.dto.CommentResponseDto;
import week13.board.service.CommentService;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/{postId}")
    public ResponseEntity<CommentResponseDto> createComment(@PathVariable Long postId, @RequestBody CommentRequestDto requestDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        CommentResponseDto commentResponseDto = commentService.createComment(postId, currentUsername, requestDto);
        return new ResponseEntity<>(commentResponseDto, HttpStatus.CREATED);
    }

    @PutMapping("/{postId}/{commentId}")
    public ResponseEntity<CommentResponseDto> updateComment(@PathVariable Long postId, @PathVariable Long commentId, @RequestBody CommentRequestDto requestDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        CommentResponseDto updatedCommentResponseDto = commentService.updateComment(postId, commentId, currentUsername, requestDto);
        return new ResponseEntity<>(updatedCommentResponseDto, HttpStatus.OK);
    }

    @DeleteMapping("/{postId}/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long postId, @PathVariable Long commentId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        commentService.deleteComment(postId, commentId, currentUsername);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

