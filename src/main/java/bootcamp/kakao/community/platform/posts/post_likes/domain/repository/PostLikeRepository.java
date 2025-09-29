package bootcamp.kakao.community.platform.posts.post_likes.domain.repository;

import bootcamp.kakao.community.platform.posts.post_likes.domain.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
}
