package bootcamp.kakao.community.security.jwt.application.dto;

import lombok.Builder;

@Builder
public record JwtTokenResponse(
        String accessToken,
        String refreshToken
) {

    /// 정적 팩토리 메서드
    public static JwtTokenResponse of(String accessToken, String refreshToken) {
        return JwtTokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

}
