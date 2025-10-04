package bootcamp.kakao.community.platform.posts.post_likes.presentation;

import bootcamp.kakao.community.common.response.ApiResponse;
import bootcamp.kakao.community.platform.posts.post_likes.application.PostLikeUseCase;
import bootcamp.kakao.community.platform.posts.post_likes.application.dto.PostLikeRequest;
import bootcamp.kakao.community.platform.posts.post_likes.presentation.swagger.PostLikeApiSpec;
import bootcamp.kakao.community.security.auth.domain.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/posts/likes")
@RequiredArgsConstructor
public class PostLikeApi implements PostLikeApiSpec {

    private final PostLikeUseCase service;

    /// 좋아요 생성
    @PostMapping
    public ApiResponse<Void> like(
            @RequestBody @Valid PostLikeRequest request,
            @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        /// 서비스
        service.like(request, customUserDetails.getId());

        /// 리턴
        return ApiResponse.created();
    }

    /// 좋아요 취소
    @DeleteMapping
    public ApiResponse<Void> unlike(
            @RequestParam Long postId,
            @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        /// 서비스
        service.unlike(postId, customUserDetails.getId());

        /// 리턴
        return ApiResponse.deleted();
    }

}
