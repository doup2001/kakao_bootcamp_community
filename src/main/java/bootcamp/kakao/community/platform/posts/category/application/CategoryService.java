package bootcamp.kakao.community.platform.posts.category.application;

import bootcamp.kakao.community.platform.posts.category.domain.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService implements CategoryUseCase{

    private final CategoryRepository repository;

}
