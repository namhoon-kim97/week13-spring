package week13.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import week13.board.domain.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findOneWithAuthoritiesByUsername(String username);
    Optional<User> findByUsername(String username);
}
