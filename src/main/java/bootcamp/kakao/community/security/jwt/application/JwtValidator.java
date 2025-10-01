package bootcamp.kakao.community.security.jwt.application;

import bootcamp.kakao.community.common.response.ErrorCode;
import bootcamp.kakao.community.platform.user.domain.entity.User;
import bootcamp.kakao.community.platform.user.domain.repository.UserRepository;
import bootcamp.kakao.community.security.auth.domain.CustomUserDetails;
import bootcamp.kakao.community.security.jwt.domain.entity.JwtRefreshToken;
import bootcamp.kakao.community.security.jwt.domain.repository.JwtRefreshTokenRepository;
import bootcamp.kakao.community.security.jwt.filter.JwtAuthenticationException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

import java.util.NoSuchElementException;

import static bootcamp.kakao.community.common.util.KeyUtil.ID_CLAIM;

@Component
@RequiredArgsConstructor
public class JwtValidator {

    private final SecretKey secretKey;
    private final UserRepository userRepository;
    /// 레디스 저장소
    private final JwtRefreshTokenRepository repository;


    // =================
    //  퍼블릭 로직
    // =================

    /// 액세스 토큰 검증
    public Authentication validateAccessToken(String accessToken) {

        try {
            /// 토큰 자체의 검증성 파악
            assertJwtValid(accessToken);

            /// 검증 완료되었다면 유저 정보 가져오기
            Long userId = getUserIdFromAccessToken(accessToken);

            /// 유저 응답
            User user = userRepository.findById(userId)
                    .orElseThrow(NoSuchElementException::new);

            /// 시큐리티에 넣을 인증 객체 생성
            CustomUserDetails customUserDetails = CustomUserDetails.of(user);
            return new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());


        } catch (ExpiredJwtException e) {
            // 토큰이 '만료'된 경우의 처리
            throw new JwtAuthenticationException(ErrorCode.ACCESS_TOKEN_EXPIRED);
        } catch (SignatureException e) {
            // 서명이 잘못된 경우의 처리
            throw new JwtAuthenticationException(ErrorCode.ACCESS_TOKEN_INVALID);
        } catch (MalformedJwtException e) {
            // 토큰 구조가 잘못된 경우의 처리
            throw new JwtAuthenticationException(ErrorCode.ACCESS_TOKEN_UNSUPPORTED);
        } catch (Exception e) {
            // 기타 예외 처리
            throw new JwtAuthenticationException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    /// 리프레쉬 토큰 검증
    public JwtRefreshToken validateRefreshToken(String deviceType, String refreshToken) {

        try {
            /// 토큰 자체의 유효성 검증 (서명, 만료일)
            assertJwtValid(refreshToken);

            /// 토큰에서 유저 ID 추출
            Long userId = getUserIdFromAccessToken(refreshToken);

            /// 리턴
            return repository.findByRefreshTokenAndDeviceTypeAndUserId(refreshToken, deviceType, userId)
                    .orElseThrow(() -> new JwtAuthenticationException(ErrorCode.REFRESH_INVALID_LOGIN));


        } catch (ExpiredJwtException e) {
            // 토큰이 '만료'된 경우의 처리
            throw new JwtAuthenticationException(ErrorCode.REFRESH_TOKEN_EXPIRED);
        } catch (SignatureException e) {
            // 서명이 잘못된 경우의 처리
            throw new JwtAuthenticationException(ErrorCode.REFRESH_TOKEN_INVALID);
        } catch (MalformedJwtException e) {
            // 토큰 구조가 잘못된 경우의 처리
            throw new JwtAuthenticationException(ErrorCode.REFRESH_TOKEN_UNSUPPORTED);
        } catch (Exception e) {
            // 기타 예외 처리
            throw new JwtAuthenticationException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    /// 로그아웃을 위해 사용하는 로직
    public void removeRefreshToken(Long userId, String deviceType, String refreshToken) {

        /// 토큰 추출
        JwtRefreshToken token = validateRefreshToken(deviceType, refreshToken);

        /// 토큰에서 유저 ID 추출
        Long userIdFromAccessToken = token.getUserId();

        /// 유저가 다르다면, 예외 발생
        if (!userIdFromAccessToken.equals(userId)) {
            throw new JwtAuthenticationException(ErrorCode.REFRESH_TOKEN_INVALID);
        }

        /// 레디스에서 토큰 삭제
        repository.delete(token);

    }

    // =================
    //  내부 공통 로직
    // =================

    /// 비밀키로 해석 가능한지 검증
    private void assertJwtValid(String token) {
         Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
    }


    /// 토큰에서 유저ID 추출하기
    private Long getUserIdFromAccessToken(String accessToken) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(accessToken)
                .getBody()
                .get(ID_CLAIM, Long.class);
    }

}
