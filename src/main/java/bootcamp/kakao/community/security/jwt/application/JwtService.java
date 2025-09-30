package bootcamp.kakao.community.security.jwt.application;

import bootcamp.kakao.community.security.jwt.domain.repository.JwtRefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtService implements JwtUsecase{

    private final JwtRefreshTokenRepository repository;

}
