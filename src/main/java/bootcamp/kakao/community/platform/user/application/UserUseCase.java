package bootcamp.kakao.community.platform.user.application;

import bootcamp.kakao.community.platform.user.application.dto.SignUpRequest;
import bootcamp.kakao.community.security.auth.domain.CustomUserDetails;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface UserUseCase {

    /// 이메일 중복 여부
    boolean checkDuplicateEmail(String email);

    /// 이메일 중복 여부
    boolean checkDuplicateNickName(String nickName);

    /// 회원가입
    void signUp(HttpServletRequest httpServletRequest, HttpServletResponse response, SignUpRequest authRequest);

    /// 회원 탈퇴
    void withdraw(HttpServletRequest httpServletRequest, HttpServletResponse response, CustomUserDetails customUserDetails);

}
