package week13.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import week13.board.domain.Comment;
import week13.board.domain.Post;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Comment> findByIdAndPost(Long commentId, Post post);
}
