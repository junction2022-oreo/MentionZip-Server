package junction.oreo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import junction.oreo.entity.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
