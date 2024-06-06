package week13.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import week13.board.domain.Post;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostUpdateRequestDto {
    private String title;
    private String contents;
    private String password;

    public Post toEntity() {
        return Post.builder()
                .title(this.title)
                .contents(this.contents)
                .password(this.password)
                .build();
    }
}
