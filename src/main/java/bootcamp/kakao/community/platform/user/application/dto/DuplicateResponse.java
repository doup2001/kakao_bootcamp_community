package bootcamp.kakao.community.platform.user.application.dto;

import lombok.Builder;

@Builder
public record DuplicateResponse(
        boolean duplicate
) {

    /// 정적 팩토리 메서드
    public static DuplicateResponse of(boolean duplicate) {
        return new DuplicateResponse(duplicate);
    }

}
