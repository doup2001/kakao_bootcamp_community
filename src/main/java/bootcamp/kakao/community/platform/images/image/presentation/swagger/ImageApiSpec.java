package bootcamp.kakao.community.platform.images.image.presentation.swagger;

import bootcamp.kakao.community.common.response.ApiResponse;
import bootcamp.kakao.community.platform.images.image.application.dto.ImageRequest;
import bootcamp.kakao.community.platform.images.image.application.dto.ImageResponse;
import bootcamp.kakao.community.security.auth.domain.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.util.List;

@Tag(name = "이미지 API", description = "이미지 발급을 위한 API 입니다.")
public interface ImageApiSpec {

    @Operation(
            summary = "파일 업로드용 URL 발급 API",
            description = "여러개의 파일의 URL 발급 받습니다."
    )
    ApiResponse<List<ImageResponse>> upload(
            @RequestBody @Valid List<ImageRequest> request,
            @AuthenticationPrincipal CustomUserDetails customUserDetails) throws IOException;


    @Operation(
            summary = "회원가입용 임시 파일 업로드용 URL API",
            description = "회원가입 용으로 임시 파일을 업로드할 수 있습니다."
    )
    ApiResponse<ImageResponse> tempUpload(
            @RequestBody @Valid ImageRequest request) throws IOException;
}
