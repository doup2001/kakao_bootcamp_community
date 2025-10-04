package bootcamp.kakao.community.platform.user.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(
        name = "[응답][유저] 중복 여부 응답 Response",
        description = "아이디, 이메일 등 유저 관련 정보의 중복 여부를 확인할 때 사용하는 DTO입니다."
)
@Builder
public record DuplicateResponse(
        @Schema(description = "중복 여부", example = "true")
        boolean duplicate
) {

    /// 정적 팩토리 메서드
    public static DuplicateResponse of(boolean duplicate) {
        return new DuplicateResponse(duplicate);
    }

}
