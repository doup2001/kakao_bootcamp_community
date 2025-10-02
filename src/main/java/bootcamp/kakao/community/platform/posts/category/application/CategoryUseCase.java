package bootcamp.kakao.community.platform.posts.category.application;

import bootcamp.kakao.community.platform.posts.category.application.dto.CategoryRequest;
import bootcamp.kakao.community.platform.posts.category.domain.entity.Category;

import java.util.Optional;

public interface CategoryUseCase{

    /// 카테고리 추가하기
    void createCategory(CategoryRequest req);

    /// 외부 로직
    Optional<Category> getCategory(Long id);
}
