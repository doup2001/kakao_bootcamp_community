package bootcamp.kakao.community.platform.posts.category.application;

import bootcamp.kakao.community.platform.posts.category.domain.entity.Category;
import bootcamp.kakao.community.platform.posts.category.domain.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService implements CategoryUseCase{

    private final CategoryRepository repository;





    // =================
    //  외부 사용 로직
    // =================
    @Override
    public Optional<Category> getCategory(Long id) {
        return Optional.empty();
    }
}
