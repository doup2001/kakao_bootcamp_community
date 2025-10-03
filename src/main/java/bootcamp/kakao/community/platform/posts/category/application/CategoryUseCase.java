package bootcamp.kakao.community.platform.posts.category.application;

import bootcamp.kakao.community.platform.posts.category.application.dto.CategoryRequest;
import bootcamp.kakao.community.platform.posts.category.domain.entity.Category;

public interface CategoryUseCase{

    /// 카테고리 추가하기 (운영자만 가능)
    void createCategory(CategoryRequest req, Long userId);

    /// 카테고리 삭제하기 (운영자만 가능)
    void deleteCategory(Long id, Long userId);

    /// 외부 로직
    Category loadCategory(Long id);
}
