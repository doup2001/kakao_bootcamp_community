package bootcamp.kakao.community.platform.images.image.domain.repository;

import bootcamp.kakao.community.platform.images.image.domain.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;
public interface ImageRepository extends JpaRepository<Image, Long> {

    /// URL 바탕으로 조회
    Optional<Image> findByUrl(String url);

    /// 여러개 URL 바탕으로 조회
    List<Image> findAllByUrlIn(List<String> urls);
}
