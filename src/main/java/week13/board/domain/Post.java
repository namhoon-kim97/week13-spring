package week13.board.domain;

import jakarta.persistence.*;
import lombok.*;
import week13.board.dto.PostUpdateRequestDto;

@Entity
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Post extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String username;
    private String contents;
    private String password;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public void update(PostUpdateRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
        this.password = requestDto.getPassword();
    }
}
