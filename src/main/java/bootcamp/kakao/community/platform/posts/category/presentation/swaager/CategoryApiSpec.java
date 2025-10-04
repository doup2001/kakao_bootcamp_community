package bootcamp.kakao.community.platform.posts.category.presentation.swaager;

import bootcamp.kakao.community.common.response.ApiResponse;
import bootcamp.kakao.community.platform.posts.category.application.dto.CategoryRequest;
import bootcamp.kakao.community.security.auth.domain.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "카테고리 API", description = "카테고리 생성/조회/삭제하는 API 입니다.")
public interface CategoryApiSpec {

    /// 카테고리 작성
    @Operation(
            summary = "카테고리 생성 API",
            description = "카테고리를 생성하는 API 입니다."
    )
    ApiResponse<Void> create(@RequestBody @Valid CategoryRequest request,
                             @AuthenticationPrincipal CustomUserDetails customUserDetails);


    /// 카테고리 삭제
    @Operation(
            summary = "카테고리 삭제 API",
            description = "카테고리를 삭제하는 API 입니다."
    )
    ApiResponse<Void> delete(
            @RequestParam Long categoryId,
            @AuthenticationPrincipal CustomUserDetails customUserDetails);

}
