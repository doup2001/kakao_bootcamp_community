package bootcamp.kakao.community.platform.user.application.dto;

import bootcamp.kakao.community.platform.user.domain.entity.User;
import lombok.Builder;

@Builder
public record UserResponse(
        Long userId,
        String name,
        String imageUrl,
        String nickname,
        String email
) {

    /// 정적 팩토리 메서드
    public static UserResponse from(User user) {
        return UserResponse.builder()
                .userId(user.getId())
                .name(user.getName())
                .imageUrl(user.getImageUrl())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .build();
    }

}
