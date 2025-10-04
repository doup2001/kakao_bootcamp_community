package bootcamp.kakao.community.platform.user.application;

import bootcamp.kakao.community.platform.user.application.dto.SignUpRequest;
import bootcamp.kakao.community.platform.user.domain.entity.User;
import bootcamp.kakao.community.security.jwt.application.dto.JwtTokenResponse;

import java.util.Optional;

public interface UserUseCase {

    /// 이메일 중복 여부
    boolean checkDuplicateEmail(String email);

    /// 이메일 중복 여부
    boolean checkDuplicateNickName(String nickName);

    /// 회원가입
    JwtTokenResponse signUp(SignUpRequest authRequest, String deviceType);

    /// 회원 탈퇴
    void withdraw(Long userId);

    /// 외부 함수
    User loadUser(Long userId);

}
