package bootcamp.kakao.community.platform.posts.category.domain.repository;

import bootcamp.kakao.community.platform.posts.category.domain.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
