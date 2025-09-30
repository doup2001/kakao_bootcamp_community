package bootcamp.kakao.community.security.jwt.application;

import bootcamp.kakao.community.common.response.ErrorCode;
import bootcamp.kakao.community.platform.user.domain.entity.User;
import bootcamp.kakao.community.platform.user.domain.repository.UserRepository;
import bootcamp.kakao.community.security.auth.domain.CustomUserDetails;
import bootcamp.kakao.community.security.jwt.domain.entity.JwtRefreshToken;
import bootcamp.kakao.community.security.jwt.domain.repository.JwtRefreshTokenRepository;
import bootcamp.kakao.community.security.jwt.filter.JwtAuthenticationException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

import static bootcamp.kakao.community.common.util.KeyUtil.*;

@Service
@RequiredArgsConstructor
public class JwtService implements JwtUseCase {

    @Value("${auth.jwt.access.expiration}")
    private long accessExpiration;

    @Value("${auth.jwt.refresh.expiration}")
    private long refreshExpiration;

    /// 의존성 주입
    private final JwtUtil jwtUtil;
    private final JwtRefreshTokenRepository repository;
    private final UserRepository userRepository;


    /// 액세스 토큰 발급
    @Override
    public void createAccessToken(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, CustomUserDetails customUserDetails) {

        /// 액세스 토큰 생성
        String accessToken = jwtUtil.createToken(customUserDetails, accessExpiration);

        /// 쿠키 또는 헤더에 전송하기 (고민)
        jwtUtil.addCookie(httpServletResponse, ACCESS_TOKEN, accessToken, accessExpiration);

    }

    /// 리프레쉬 토큰 발급
    @Override
    public void createRefreshToken(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, CustomUserDetails customUserDetails) {

        /// 리프레쉬 토큰 생성
        String refreshToken = jwtUtil.createToken(customUserDetails, refreshExpiration);

        /// 요청 종류 파악
        String deviceType = getDeviceType(httpServletRequest);

        /// 객체 생성
        var token = JwtRefreshToken.of(customUserDetails.getId(), refreshToken, deviceType, refreshExpiration);

        /// 레디스에 저장하기
        JwtRefreshToken response = repository.save(token);

        /// 쿠키에 전송하기
        jwtUtil.addCookie(httpServletResponse, REFRESH_TOKEN, response.getRefreshToken(), refreshExpiration);

    }

    /// 액세스 토큰 검증
    @Override
    public void validateAccessToken(HttpServletRequest request) {

        /// 검증
        String accessToken = jwtUtil.extractToken(request, ACCESS_TOKEN)
                .orElseThrow(() -> new JwtAuthenticationException(ErrorCode.TOKEN_NOT_FOUND));

        try {
            /// 토큰 자체의 검증성 파악
            jwtUtil.assertJwtValid(accessToken);

            Long userIdFromAccessToken = jwtUtil.getUserIdFromAccessToken(accessToken);

            /// 인증 설정
            User user = userRepository.findById(userIdFromAccessToken)
                    .orElseThrow(NoSuchElementException::new);

            /// 시큐리티에 유저 저장
            CustomUserDetails customUserDetails = CustomUserDetails.of(user);
            Authentication authentication = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (ExpiredJwtException e) {
            // 토큰이 '만료'된 경우의 처리
            throw new JwtAuthenticationException(ErrorCode.TOKEN_EXPIRED);
        } catch (SignatureException e) {
            // 서명이 잘못된 경우의 처리
            throw new JwtAuthenticationException(ErrorCode.TOKEN_INVALID);
        } catch (MalformedJwtException e) {
            // 토큰 구조가 잘못된 경우의 처리
            throw new JwtAuthenticationException(ErrorCode.TOKEN_UNSUPPORTED);
        } catch (Exception e) {
            // 기타 예외 처리
            throw new JwtAuthenticationException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    /// 리프레쉬 토큰 검증, 재발급에서 사용
    @Override
    public JwtRefreshToken validateRefreshToken(HttpServletRequest request) {

        /// 토큰 추출
        String refreshToken = jwtUtil.extractToken(request, REFRESH_TOKEN)
                .orElseThrow(() -> new JwtAuthenticationException(ErrorCode.TOKEN_NOT_FOUND));

        /// 토큰 자체의 유효성 검증 (서명, 만료일)
        jwtUtil.assertJwtValid(refreshToken);

        /// Redis에 해당 토큰이 저장되어 있는지 확인
        String deviceType = getDeviceType(request);

        /// 리턴
        return repository.findByRefreshTokenAndDeviceType(refreshToken, deviceType)
                .orElseThrow(() -> new JwtAuthenticationException(ErrorCode.INVALID_LOGIN));

    }


    /// Header에서 어떤 디바이스인지 체크
    private String getDeviceType(HttpServletRequest httpServletRequest) {
        return httpServletRequest.getHeader("User-Agent");
    }

}
