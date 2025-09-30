package bootcamp.kakao.community.platform.posts.category.presentation;

import bootcamp.kakao.community.platform.posts.category.application.CategoryUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/category")
@RequiredArgsConstructor
public class CategoryApi {

    private final CategoryUseCase service;

}
