package week13.board.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import week13.board.dto.CommentRequestDto;

@Entity
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Comment extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 번호

    private String content; // 답변 내용

    // 질문
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    // userid
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Comment(String content, Post post, User user) {
        this.content = content;
        this.post = post;
        this.user = user;
    }

    public void update(CommentRequestDto commentDto) {
        this.content = commentDto.getComment();
    }
}
