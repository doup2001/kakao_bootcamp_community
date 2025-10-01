package bootcamp.kakao.community.security.jwt.application;

import bootcamp.kakao.community.security.auth.domain.CustomUserDetails;
import bootcamp.kakao.community.security.jwt.domain.entity.JwtRefreshToken;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface JwtUseCase {

    /// 액세스 토큰 발급하기
    void createAccessToken(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, CustomUserDetails customUserDetails);

    /// 리프레쉬 토큰 발급하기
    void createRefreshToken(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, CustomUserDetails customUserDetails);

    /// 쿠키에서 액세스 토큰 검증하기
    void validateAccessToken(HttpServletRequest request);

    /// 쿠키에서 리프레쉬 토큰 검증하기
    JwtRefreshToken validateRefreshToken(HttpServletRequest request);

}
