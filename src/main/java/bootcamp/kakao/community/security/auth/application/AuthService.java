package bootcamp.kakao.community.security.auth.application;

import bootcamp.kakao.community.common.response.ErrorCode;
import bootcamp.kakao.community.platform.user.domain.entity.User;
import bootcamp.kakao.community.platform.user.domain.repository.UserRepository;
import bootcamp.kakao.community.security.auth.application.dto.LoginRequest;
import bootcamp.kakao.community.security.jwt.application.JwtProvider;
import bootcamp.kakao.community.security.jwt.application.JwtValidator;
import bootcamp.kakao.community.security.jwt.application.dto.JwtTokenResponse;
import bootcamp.kakao.community.security.jwt.application.dto.JwtTokenRequest;
import bootcamp.kakao.community.security.jwt.domain.entity.JwtRefreshToken;
import bootcamp.kakao.community.security.jwt.filter.JwtAuthenticationException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService implements AuthUseCase {

    private final UserRepository repository;
    /// 패스워드 암호화
    private final BCryptPasswordEncoder passwordEncoder;

    /// 토큰 의존성
    private final JwtValidator jwtValidator;
    private final JwtProvider jwtProvider;

    // =================
    //  퍼블릭 로직
    // =================

    /// 로그인
    @Override
    public JwtTokenResponse login(LoginRequest request, String deviceType) {

        /// DB 검증
        User user = repository.findByEmail(request.email())
                .orElseThrow(() -> new NoSuchElementException("해당 이메일을 가진 유저가 없습니다"));

        /// 패스워드 비교
        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new IllegalArgumentException("로그인할 수 없습니다.");
        }

        /// 로그인했다면, JWT 발급하기
        var jwtRequest = JwtTokenRequest.from(user);

        String accessToken = jwtProvider.createAccessToken(jwtRequest);
        String refreshToken = jwtProvider.createRefreshToken(deviceType, jwtRequest);

        return JwtTokenResponse.of(accessToken, refreshToken);
    }

    /// 로그아웃
    @Override
    public void logout(Long userId, String deviceType, Optional<String> refreshToken) {

        /// 없다면 예외처리
        if (refreshToken.isEmpty()) {
            throw new JwtAuthenticationException(ErrorCode.REFRESH_INVALID_LOGIN);
        }

        /// 레디스에서 삭제하도록 로직 수행
        jwtValidator.removeRefreshToken(userId, deviceType, refreshToken.get());
    }

    /// 토큰 재발급
    @Override
    public JwtTokenResponse reissue(String deviceType, Optional<String> refreshToken) {

        /// 없다면 예외처리
        if (refreshToken.isEmpty()) {
            throw new JwtAuthenticationException(ErrorCode.REFRESH_INVALID_LOGIN);
        }

        /// 존재하는 리프레쉬 토큰 검증
        JwtRefreshToken token = jwtValidator.validateRefreshToken(deviceType, refreshToken.get());

        /// 리프레쉬 토큰 바탕으로 조회
        User user = repository.findById(token.getUserId())
                .orElseThrow(() -> new NoSuchElementException("해당 아이디를 가진 유저가 없습니다"));

        /// 인증된 유저에게 JWT 발급하기
        var jwtRequest = JwtTokenRequest.from(user);

        String newAccessToken = jwtProvider.createAccessToken(jwtRequest);

        return JwtTokenResponse.of(newAccessToken, null);
    }
}

