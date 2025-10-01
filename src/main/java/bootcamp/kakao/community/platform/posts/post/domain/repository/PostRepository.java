package bootcamp.kakao.community.platform.posts.post.domain.repository;

import bootcamp.kakao.community.platform.posts.post.domain.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    Optional<Post> findByIdAndDeletedIsFalse(Long id);


}
