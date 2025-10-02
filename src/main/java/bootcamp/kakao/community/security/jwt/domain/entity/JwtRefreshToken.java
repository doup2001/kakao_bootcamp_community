package bootcamp.kakao.community.security.jwt.domain.entity;

import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

import java.util.concurrent.TimeUnit;

import static bootcamp.kakao.community.common.util.KeyUtil.getRefreshTokenKey;

@RedisHash
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class JwtRefreshToken {

    @Id
    private String id;

    @Indexed
    private Long userId;

    @Indexed
    private String refreshToken;

    @Indexed
    private String deviceType;

    @TimeToLive(unit = TimeUnit.SECONDS)
    private long expireTime;

    /// 빌더 생성자
    @Builder
    public JwtRefreshToken(Long userId, String refreshToken, String deviceType, long expireTime) {
        this.id = getRefreshTokenKey(userId, deviceType);  /// RedisUtil 통해 Key 발급
        this.userId = userId;
        this.refreshToken = refreshToken;
        this.deviceType = deviceType;
        this.expireTime = expireTime;
    }

    /// 정적 팩토리 메서드
    public static JwtRefreshToken of(Long userId, String refreshToken, String deviceType, long expireTime) {
        return JwtRefreshToken.builder()
                .userId(userId)
                .refreshToken(refreshToken)
                .deviceType(deviceType)
                .expireTime(expireTime)
                .build();
    }

}
