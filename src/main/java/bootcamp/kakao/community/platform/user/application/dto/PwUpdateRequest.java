package bootcamp.kakao.community.platform.user.application.dto;

public record PwUpdateRequest(
        String oldPassword,
        String newPassword,
        String confirmPassword
) {
}
