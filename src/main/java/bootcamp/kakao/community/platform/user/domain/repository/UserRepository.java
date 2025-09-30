package bootcamp.kakao.community.platform.user.domain.repository;

import bootcamp.kakao.community.platform.user.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
