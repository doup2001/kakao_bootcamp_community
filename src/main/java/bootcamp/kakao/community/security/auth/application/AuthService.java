package bootcamp.kakao.community.security.auth.application;

import bootcamp.kakao.community.platform.user.domain.entity.User;
import bootcamp.kakao.community.platform.user.domain.repository.UserRepository;
import bootcamp.kakao.community.security.auth.application.dto.LoginRequest;
import bootcamp.kakao.community.security.auth.domain.CustomUserDetails;
import bootcamp.kakao.community.security.jwt.application.JwtUseCase;
import bootcamp.kakao.community.security.jwt.domain.entity.JwtRefreshToken;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class AuthService implements AuthUseCase {

    private final UserRepository repository;
    private final JwtUseCase tokenService;

    /// 패스워드 암호화
    private final BCryptPasswordEncoder passwordEncoder;

    /// 로그인
    @Override
    public void login(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, LoginRequest request) {

        /// DB 검증
        User user = repository.findByEmail(request.email())
                .orElseThrow(() -> new NoSuchElementException("해당 이메일을 가진 유저가 없습니다"));

        /// 패스워드 비교
        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new IllegalArgumentException("로그인할 수 없습니다.");
        }

        /// 시큐리티에 유저 저장
        CustomUserDetails customUserDetails = CustomUserDetails.of(user);
        Authentication authentication = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);


        /// 액세스 토큰 및 리프레쉬 토큰 발급
        tokenService.createAccessToken(httpServletRequest, httpServletResponse, customUserDetails);
        tokenService.createRefreshToken(httpServletRequest, httpServletResponse, customUserDetails);

    }

    /// 로그아웃
    @Override
    public void logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

    }

    /// 토큰 재발급
    @Override
    public void reissue(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

        /// 리프레쉬 토큰이 제대로 존재하는지 체크
        /// 리프레시 토큰이 있는지 검증
        JwtRefreshToken refreshToken = tokenService.validateRefreshToken(httpServletRequest);

        User user = repository.findById(refreshToken.getUserId())
                .orElseThrow(() -> new NoSuchElementException("해당 아이디를 가진 유저가 없습니다"));

        /// 시큐리티에 유저 저장
        CustomUserDetails customUserDetails = CustomUserDetails.of(user);
        Authentication authentication = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        /// 새롭게 액세스 토큰 발급
        tokenService.createAccessToken(httpServletRequest, httpServletResponse, customUserDetails);

    }
}

