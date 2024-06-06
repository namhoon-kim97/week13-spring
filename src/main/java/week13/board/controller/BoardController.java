package week13.board.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import week13.board.domain.Post;
import week13.board.dto.PostRequestDto;
import week13.board.dto.PostResponseDto;
import week13.board.dto.PostUpdateRequestDto;
import week13.board.service.BoardService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {
    private final BoardService boardService;

    @GetMapping
    public String home(Model model) {
        List<PostResponseDto> posts = boardService.getAllPosts().stream()
                .map(PostResponseDto::of)
                .collect(Collectors.toList());
        model.addAttribute("posts", posts);
        return "index";
    }

    @PostMapping
    public String createPost(@Valid @RequestBody PostRequestDto requestDto){
        Post post = boardService.createPost(requestDto.toEntity());
        return "redirect:/board";
    }

    @GetMapping("/{id}")
    public String displayPost(Model model, @PathVariable("id") Long id){
        Post post = boardService.findPostById(id);
        PostResponseDto postDto = PostResponseDto.of(post);
        model.addAttribute("post", postDto);
        return "post";
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updatePost(@PathVariable("id") Long id, @RequestBody PostUpdateRequestDto requestDto) {
        Long updatedPostId = boardService.updatePost(id, requestDto);
        return ResponseEntity.ok(updatedPostId);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id){
        boardService.deletePost(id);
        return "index";
    }
}
