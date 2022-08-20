package junction.oreo.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import junction.oreo.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByMid(String mid);
}
