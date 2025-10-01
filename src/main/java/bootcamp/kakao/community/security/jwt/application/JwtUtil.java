package bootcamp.kakao.community.security.jwt.application;

import bootcamp.kakao.community.security.auth.domain.CustomUserDetails;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

import static bootcamp.kakao.community.common.util.KeyUtil.ID_CLAIM;

@Component
@RequiredArgsConstructor
public class JwtUtil {

    private final SecretKey secretKey;

    /// 키 발급 내부 로직
    protected String createToken(CustomUserDetails customUserDetails, long expiredTime) {

        /// 현재 시간 기준으로 생성
        Instant now = Instant.now();

        /// 토큰 만료일 설정
        Date expiredAt = Date.from(now.plus(Duration.ofMinutes(expiredTime)));

        /// JWT 내용 생성
        Map<String, Object> claims = new HashMap<>();
        claims.put(ID_CLAIM, customUserDetails.getId());

        return Jwts.builder()
                .setIssuer("kakao-bootcamp-community") // 작성자
                .setClaims(claims)  // 페이로드
                .setIssuedAt(Date.from(now)) // 발급 시간
                .setExpiration(expiredAt) // 만료 시간
                .signWith(secretKey, SignatureAlgorithm.HS256)  // 비밀키 서명
                .compact();
    }

    /// 요청한 쿠키에서 토큰 가져오는 내부 로직
    protected Optional<String> extractToken(HttpServletRequest httpServletRequest, String type) {

        /// 쿠키 가져오기
        Cookie[] cookies = httpServletRequest.getCookies();

        /// 쿠키가 존재한다면,
        if (cookies != null) {
            for (Cookie cookie : cookies) {

                /// 해당 타입의 쿠키만 추출
                if (cookie.getName().equals(type)) {
                    return Optional.of(cookie.getValue());
                }
            }
        }
        return Optional.empty();
    }

    /// 비밀키로 해석 가능한지 검증
    protected void assertJwtValid(String token) {
        Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token);
    }

    /// 토큰을 쿠키에 저장하기
    protected void addCookie(HttpServletResponse httpServletResponse, String type, String jwtToken, long expiredTime) {

        /// 쿠키 생성
        Cookie cookie = new Cookie(type, jwtToken);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge((int) expiredTime);

        // 쿠키 만료 시간 (초 단위)

        /// 쿠키 저장
        httpServletResponse.addCookie(cookie);
    }

    /// 토큰에서 유저ID 추출하기
    protected Long getUserIdFromAccessToken(String accessToken) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(accessToken)
                .getBody()
                .get(ID_CLAIM, Long.class);
    }



}


