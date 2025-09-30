package bootcamp.kakao.community.security.auth.application;

import bootcamp.kakao.community.security.auth.application.dto.LoginRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthUseCase {

    /// 로그인
    void login(HttpServletRequest httpServletRequest, HttpServletResponse response, LoginRequest request);

    /// 로그아웃
    void logout(HttpServletRequest httpServletRequest, HttpServletResponse response);

    /// 토큰 재발급
    void reissue(HttpServletRequest httpServletRequest, HttpServletResponse response);

}
