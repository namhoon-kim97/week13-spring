package week13.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import week13.board.domain.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
