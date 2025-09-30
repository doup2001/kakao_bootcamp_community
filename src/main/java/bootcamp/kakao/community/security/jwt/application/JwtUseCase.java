package bootcamp.kakao.community.security.jwt.application;

import bootcamp.kakao.community.platform.user.domain.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface JwtUseCase {

    /// 액세스 토큰 발급하기
    void createAccessToken(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, User user);

    /// 리프레쉬 토큰 발급하기
    void createRefreshToken(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, User user);

    /// 쿠키에서 토큰 검증하기
    boolean validateToken(HttpServletRequest httpServletRequest, User user, String type);


}
