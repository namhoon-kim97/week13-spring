package week13.board.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import week13.board.domain.Post;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostRequestDto {
    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Contents is required")
    private String contents;

    @NotBlank(message = "Password is required")
    private String password;

    public Post toEntity() {
        return Post.builder()
                .title(this.title)
                .username(this.username)
                .contents(this.contents)
                .password(this.password)
                .build();
    }
}
