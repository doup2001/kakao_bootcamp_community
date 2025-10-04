package bootcamp.kakao.community.platform.user.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "[요청][유저] 비밀번호 변경 요청 Request", description = "사용자의 비밀번호 변경 요청을 위한 DTO입니다.")
public record PwUpdateRequest(
        @Schema(description = "기존 비밀번호", example = "oldPass123!")
        String oldPassword,
        @Schema(description = "새 비밀번호", example = "newPass456!")
        String newPassword,
        @Schema(description = "새 비밀번호 확인", example = "newPass456!")
        String confirmPassword
) {
}
