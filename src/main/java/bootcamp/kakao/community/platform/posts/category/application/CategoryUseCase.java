package bootcamp.kakao.community.platform.posts.category.application;

import bootcamp.kakao.community.platform.posts.category.domain.entity.Category;

import java.util.Optional;

public interface CategoryUseCase{


    Optional<Category> getCategory(Long id);
}
