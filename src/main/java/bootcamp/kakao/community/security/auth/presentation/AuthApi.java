package bootcamp.kakao.community.security.auth.presentation;

import bootcamp.kakao.community.common.response.ApiResponse;
import bootcamp.kakao.community.security.auth.application.AuthUseCase;
import bootcamp.kakao.community.security.auth.application.dto.LoginRequest;
import bootcamp.kakao.community.security.auth.domain.CustomUserDetails;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthApi {

    private final AuthUseCase service;

    /**
     * 로그인
     */
    @PostMapping
    public ApiResponse<Void> login(
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse,
            @RequestBody @Valid LoginRequest request
    ){
        /// 서비스 로직 실행
        service.login(httpServletRequest, httpServletResponse, request);

        /// 리턴
        return ApiResponse.created();
    }

    /**
     * 로그아웃
     */
    @DeleteMapping
    public ApiResponse<Void> logout(
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse,
            @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        /// 서비스 로직 실행
        service.logout(httpServletRequest, httpServletResponse);

        /// 리턴
        return ApiResponse.deleted();
    }

    /**
     * 토큰 재발급
     */
    @PutMapping
    public ApiResponse<Void> reissue(
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse
    ) {

        /// 서비스 로직 실행
        service.reissue(httpServletRequest, httpServletResponse);

        /// 리턴
        return ApiResponse.updated();
    }


}
