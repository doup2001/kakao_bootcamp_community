package bootcamp.kakao.community.platform.user.application.dto;

import bootcamp.kakao.community.platform.user.domain.entity.User;
import lombok.Builder;

@Builder
public record MyPageResponse(
        Long userId,
        String name,
        String imageUrl,
        String nickname,
        String email,
        String role
) {

    /// 정적 팩토리 메서드
    public static MyPageResponse from(User user) {
        return MyPageResponse.builder()
                .userId(user.getId())
                .name(user.getName())
                .imageUrl(user.getImageUrl())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .role(user.getRole().getLabel())
                .build();
    }
}
