package bootcamp.kakao.community.platform.posts.category.application;

import bootcamp.kakao.community.platform.posts.category.application.dto.CategoryRequest;
import bootcamp.kakao.community.platform.posts.category.domain.entity.Category;
import bootcamp.kakao.community.platform.posts.category.domain.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService implements CategoryUseCase{

    private final CategoryRepository repository;

    /// 카테고리 추가하기
    @Override
    @Transactional
    public void createCategory(CategoryRequest req) {

        Category parent = null;

        /// 부모 값이 존재한다면,
        if (req.parentId() != null) {
            parent = repository.findById(req.parentId())
                    .orElseThrow(NoSuchElementException::new);
        }
        Category reqCategory = Category.of(parent, req.name());

        /// 저장
        repository.save(reqCategory);
    }

    // =================
    //  외부 사용 로직
    // =================
    @Override
    public Optional<Category> getCategory(Long id) {
        return Optional.empty();
    }
}
