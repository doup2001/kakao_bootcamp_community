package bootcamp.kakao.community.platform.posts.post.domain.repository;

import bootcamp.kakao.community.platform.posts.post.domain.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
