package week13.board.util;

import week13.board.domain.Comment;
import week13.board.dto.CommentResponseDto;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CommentUtils {
    public static List<CommentResponseDto> convertAndSortComments(List<Comment> comments) {
        if (comments == null) {
            return Collections.emptyList();
        }
        return comments.stream()
                .sorted((c1, c2) -> c2.getCreatedAt().compareTo(c1.getCreatedAt()))
                .map(CommentResponseDto::of)
                .collect(Collectors.toList());
    }
}
