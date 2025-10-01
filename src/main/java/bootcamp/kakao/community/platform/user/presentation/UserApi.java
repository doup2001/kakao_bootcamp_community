package bootcamp.kakao.community.platform.user.presentation;

import bootcamp.kakao.community.common.response.ApiResponse;
import bootcamp.kakao.community.platform.user.application.UserUseCase;
import bootcamp.kakao.community.platform.user.application.dto.DuplicateResponse;
import bootcamp.kakao.community.platform.user.application.dto.SignUpRequest;
import bootcamp.kakao.community.security.auth.domain.CustomUserDetails;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UserApi {

    /// 유저 서비스
    private final UserUseCase service;

    /**
     * RestAPI 일관성을 위해
     * 회원가입 엔드포인트는 "/v1/users" 로
     */
    @PostMapping
    public ApiResponse<Void> signup(
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse,
            @RequestBody @Valid SignUpRequest request) {

        /// 서비스 로직
        service.signUp(httpServletRequest, httpServletResponse, request);

        /// 리턴
        return ApiResponse.created();
    }

    /**
     * 유저 소프트 삭제
     */
    @PutMapping
    public ApiResponse<Void> delete(
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse,
            @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        /// 서비스 로직
        service.withdraw(httpServletRequest, httpServletResponse, customUserDetails);

        /// 응답
        return ApiResponse.deleted();
    }

    /**
     * 이메일 중복 체크
     */
    @GetMapping("/email")
    public ApiResponse<DuplicateResponse> checkEmail(@RequestParam String email) {

        /// 서비스 로직
        boolean duplicateEmail = service.checkDuplicateEmail(email);

        /// 리턴
        return ApiResponse.ok(DuplicateResponse.of(duplicateEmail));
    }

    /**
     * 닉네임 중복 체크
     */
    @GetMapping("/nickname")
    public ApiResponse<DuplicateResponse> checkNickName(@RequestParam String nickName) {

        /// 서비스 로직
        boolean duplicateNickName = service.checkDuplicateNickName(nickName);

        /// 리턴
        return ApiResponse.ok(DuplicateResponse.of(duplicateNickName));
    }

}
