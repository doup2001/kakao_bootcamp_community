package bootcamp.kakao.community.platform.images.image.presentation;

import bootcamp.kakao.community.common.response.ApiResponse;
import bootcamp.kakao.community.platform.images.image.application.ImageUseCase;
import bootcamp.kakao.community.platform.images.image.application.dto.ImageRequest;
import bootcamp.kakao.community.platform.images.image.application.dto.ImageResponse;
import bootcamp.kakao.community.security.auth.domain.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/v1/images")
@RequiredArgsConstructor
public class ImageApi {

    private final ImageUseCase service;

    /**
     * 파일 업로드용 URL 발급
     */
    @PostMapping
    public ApiResponse<ImageResponse> upload(
            @RequestBody @Valid ImageRequest request,
            @AuthenticationPrincipal CustomUserDetails customUserDetails) throws IOException {

        /// 서비스
        ImageResponse response = service.upload(request, customUserDetails.getId());

        /// 응답 리턴
        return ApiResponse.created(response);
    }


    /**
     * 회원가입에서 사용하는 업로드용 URL 발급
     */
    @PostMapping("/temp")
    public ApiResponse<ImageResponse> tempUpload(
            @RequestBody @Valid ImageRequest request) throws IOException {

        /// 서비스
        ImageResponse response = service.uploadTemporaryImage(request);

        /// 응답 리턴
        return ApiResponse.created(response);
    }

}
