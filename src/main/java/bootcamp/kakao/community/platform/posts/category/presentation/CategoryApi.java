package bootcamp.kakao.community.platform.posts.category.presentation;

import bootcamp.kakao.community.common.response.ApiResponse;
import bootcamp.kakao.community.platform.posts.category.application.CategoryUseCase;
import bootcamp.kakao.community.platform.posts.category.application.dto.CategoryRequest;
import bootcamp.kakao.community.platform.posts.category.presentation.swaager.CategoryApiSpec;
import bootcamp.kakao.community.security.auth.domain.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/category")
@RequiredArgsConstructor
public class CategoryApi implements CategoryApiSpec {

    private final CategoryUseCase service;

    /// 카테고리 생성
    @PostMapping()
    public ApiResponse<Void> create(@RequestBody @Valid CategoryRequest request,
                                    @AuthenticationPrincipal CustomUserDetails customUserDetails) {


        /// 서비스
        service.createCategory(request, customUserDetails.getId());

        /// 리턴
        return ApiResponse.created();
    }

    /// 카테고리 삭제
    @DeleteMapping()
    public ApiResponse<Void> delete(
            @RequestParam Long categoryId,
            @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        /// 서비스
        service.deleteCategory(categoryId, customUserDetails.getId());

        /// 리턴
        return ApiResponse.deleted();
    }

}
