package bootcamp.kakao.community.platform.images.post_images.domain.repository;

import bootcamp.kakao.community.platform.images.post_images.domain.entity.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostImageRepository extends JpaRepository<PostImage, Long> {
}
