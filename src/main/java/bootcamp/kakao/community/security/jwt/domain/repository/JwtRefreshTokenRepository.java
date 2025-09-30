package bootcamp.kakao.community.security.jwt.domain.repository;

import bootcamp.kakao.community.security.jwt.domain.entity.JwtRefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface JwtRefreshTokenRepository extends CrudRepository<JwtRefreshToken, String> {
}
