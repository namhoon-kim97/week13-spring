package week13.board.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import week13.board.domain.Post;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Post, Long> {
    @EntityGraph(attributePaths = "comments")
    List<Post> findAllByOrderByCreatedAtDesc();

    @EntityGraph(attributePaths = "comments")
    Optional<Post> findById(Long id);
}
