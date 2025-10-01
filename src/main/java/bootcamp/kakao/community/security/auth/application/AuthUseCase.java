package bootcamp.kakao.community.security.auth.application;

import bootcamp.kakao.community.security.auth.application.dto.LoginRequest;
import bootcamp.kakao.community.security.jwt.application.dto.JwtTokenResponse;

import java.util.Optional;

public interface AuthUseCase {

    /// 로그인
    JwtTokenResponse login(LoginRequest request, String deviceType);

    /// 로그아웃
    void logout(Long userId, String deviceType, Optional<String> refreshToken);

    /// 토큰 재발급
    JwtTokenResponse reissue(String deviceType, Optional<String> refreshToken);

}
