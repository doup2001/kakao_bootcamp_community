package bootcamp.kakao.community.security.jwt.domain.repository;

import bootcamp.kakao.community.security.jwt.domain.entity.JwtRefreshToken;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface JwtRefreshTokenRepository extends CrudRepository<JwtRefreshToken, String> {

    Optional<JwtRefreshToken> findByUserIdAndDeviceType(Long userId, String deviceType);
}
