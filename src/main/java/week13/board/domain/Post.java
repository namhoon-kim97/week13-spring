package week13.board.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import week13.board.dto.PostUpdateRequestDto;

import java.util.List;

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
    private String contents;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments;

    public Post(String title, String contents, User user) {
        this.title = title;
        this.contents = contents;
        this.user = user;
    }

    public void update(PostUpdateRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
    }
}
