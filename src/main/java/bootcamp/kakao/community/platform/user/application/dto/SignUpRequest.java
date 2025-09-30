package bootcamp.kakao.community.platform.user.application.dto;

public record SignUpRequest(
        String name,
        String imageUrl,
        String nickname,
        String email,
        String password,
        String confirmPassword
) {

}
