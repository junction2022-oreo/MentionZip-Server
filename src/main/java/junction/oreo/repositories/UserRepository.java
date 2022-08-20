package junction.oreo.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import junction.oreo.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    // @EntityGraph을 선언하면 lazy가 아닌 eager로 조회한다.
    @EntityGraph(attributePaths = "authorities")
    Optional<User> findOneWithAuthoritiesByUsername(String username);
}
