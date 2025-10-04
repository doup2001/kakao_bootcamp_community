package bootcamp.kakao.community.platform.user.application.dto;

public record UserUpdateRequest(
        String nickname,
        String imageUrl
) {

}
