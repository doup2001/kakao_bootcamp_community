package bootcamp.kakao.community.platform.images.image.domain.repository;

import bootcamp.kakao.community.platform.images.image.domain.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Long> {
    Optional<Image> findByUrl(String url);
}
