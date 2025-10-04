package bootcamp.kakao.community.platform.images.post_images.domain.repository;

import bootcamp.kakao.community.platform.images.post_images.domain.entity.PostImage;
import bootcamp.kakao.community.platform.posts.post.domain.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostImageRepository extends JpaRepository<PostImage, Long> {
    List<PostImage> findByPost(Post post);
}
