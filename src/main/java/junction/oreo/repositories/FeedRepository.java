package junction.oreo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import junction.oreo.entity.Feed;
import junction.oreo.entity.Member;
import junction.oreo.enums.CategoryType;

public interface FeedRepository extends JpaRepository<Feed, Long> {

    List<Feed> findByMemberAndCategory(Member member, CategoryType category);
}
