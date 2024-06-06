package week13.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import week13.board.domain.Post;

import java.util.List;

public interface BoardRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByOrderByCreatedAtDesc();
}
