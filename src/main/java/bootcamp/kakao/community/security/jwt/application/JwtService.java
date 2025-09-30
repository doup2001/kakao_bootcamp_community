package bootcamp.kakao.community.security.jwt.application;

import bootcamp.kakao.community.platform.user.domain.entity.User;
import bootcamp.kakao.community.security.jwt.domain.entity.JwtRefreshToken;
import bootcamp.kakao.community.security.jwt.domain.repository.JwtRefreshTokenRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import static bootcamp.kakao.community.common.util.KeyUtil.*;

@Service
@RequiredArgsConstructor
public class JwtService implements JwtUseCase {

    @Value("${auth.jwt.access.expiration}")
    private long accessExpiration;

    @Value("${auth.jwt.refresh.expiration}")
    private long refreshExpiration;

    /// 의존성 주입
    private final JwtRefreshTokenRepository repository;
    private final SecretKey secretKey;

    @Override
    public void createAccessToken(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, User user) {

        /// 액세스 토큰 생성
        String accessToken = createToken(user, accessExpiration);

        /// 쿠키 또는 헤더에 전송하기 (고민)
        addCookie(httpServletResponse, ACCESS_TOKEN, accessToken);

    }

    @Override
    public void createRefreshToken(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, User user) {

        /// 리프레쉬 토큰 생성
        String refreshToken = createToken(user, refreshExpiration);

        /// 요청 종류 파악
        String deviceType = getDeviceType(httpServletRequest);

        /// 객체 생성
        var token = JwtRefreshToken.of(user.getId(), refreshToken, deviceType);

        /// 레디스에 저장하기
        JwtRefreshToken response = repository.save(token);

        /// 쿠키에 전송하기
        addCookie(httpServletResponse, REFRESH_TOKEN, response.getRefreshToken());

    }

    @Override
    public boolean validateToken(HttpServletRequest httpServletRequest, User user, String type) {

        /// 쿠키 값 가져오기
        String jwtToken = extractToken(httpServletRequest, type);

        /// 요청 종류 파악
        String deviceType = getDeviceType(httpServletRequest);

        /// 유저랑 검증 비교
        JwtRefreshToken token = repository.findByUserIdAndDeviceType((user.getId()), deviceType)
                .orElseThrow(() -> new NoSuchElementException("해당 디바이스에서 리프레쉬 토큰이 존재하지 않습니다.")); /// 없다면 예외 발생

        /// 토큰 자체의 검증성 파악
        assertJwtValid(jwtToken);

        /// 저장 값과 해당 값이 일치하는지 비교
        return token.getRefreshToken().equals(jwtToken);

    }


    /// 키 발급 내부 로직
    private String createToken(User user, long expiredTime) {

        /// 현재 시간 기준으로 생성
        Instant now = Instant.now();

        /// 토큰 만료일 설정
        Date expiredAt = Date.from(now.plus(Duration.ofMinutes(expiredTime)));

        /// JWT 내용 생성
        Map<String, Object> claims = new HashMap<>();
        claims.put(ID_CLAIM, user.getId());

        return Jwts.builder()
                .setIssuer("kakao-bootcamp-community") // 작성자
                .setClaims(claims)  // 페이로드
                .setIssuedAt(Date.from(now)) // 발급 시간
                .setExpiration(expiredAt) // 만료 시간
                .signWith(secretKey, SignatureAlgorithm.HS256)  // 비밀키 서명
                .compact();
    }

    /// 요청한 쿠키에서 토큰 가져오는 내부 로직
    private String extractToken(HttpServletRequest httpServletRequest, String type) {

        /// 쿠키 가져오기
        Cookie[] cookies = httpServletRequest.getCookies();

        /// 쿠키가 존재한다면,
        if (cookies != null) {
            for (Cookie cookie : cookies) {

                /// 해당 타입의 쿠키만 추출
                if (cookie.getName().equals(type)) {
                    return cookie.getValue();
                }
            }
        }
        throw new NoSuchElementException();
    }

    /// 비밀키로 해석 가능한지 검증
    private void assertJwtValid(String jwt) {
        Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(jwt); // 유효하지 않으면 예외 발생
    }


    /// 토큰을 쿠키에 저장하기
    private void addCookie(HttpServletResponse httpServletResponse, String type, String jwtToken) {

        /// 쿠키 생성
        Cookie token = new Cookie(type, jwtToken);
        token.setPath("/");

        /// 쿠키 저장
        httpServletResponse.addCookie(token);
    }


    /// Header에서 어떤 디바이스인지 체크
    private String getDeviceType(HttpServletRequest httpServletRequest) {
        return httpServletRequest.getHeader("User-Agent");
    }

}
