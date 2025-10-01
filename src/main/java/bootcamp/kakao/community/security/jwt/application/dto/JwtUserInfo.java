package bootcamp.kakao.community.security.jwt.application.dto;

import bootcamp.kakao.community.platform.user.domain.entity.User;
import bootcamp.kakao.community.platform.user.domain.entity.UserRole;
import lombok.Builder;

@Builder
public record JwtUserInfo(
        Long userId,
        UserRole role
) {

    /// 정적 팩토리 메서드
    public static JwtUserInfo from(User user) {
        return JwtUserInfo.builder()
                .userId(user.getId())
                .role(user.getRole())
                .build();
    }

}
