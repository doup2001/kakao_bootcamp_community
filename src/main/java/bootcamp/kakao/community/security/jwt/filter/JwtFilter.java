package bootcamp.kakao.community.security.jwt.filter;

import bootcamp.kakao.community.security.jwt.application.JwtUseCase;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUseCase tokenService;
    private final JwtFailureHandler jwtFailureHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // OPTIONS 필터에서 타지않도록 넣는다.
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            /// 검증하기
            tokenService.validateAccessToken(request);

            /// 다음 필터로 진행
            filterChain.doFilter(request, response);
        } catch (JwtAuthenticationException ex) {
            /// 실패 핸들러로 이동
            jwtFailureHandler.commence(request, response, ex);
        }
    }
}
