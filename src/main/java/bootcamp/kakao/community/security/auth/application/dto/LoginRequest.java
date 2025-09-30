package bootcamp.kakao.community.security.auth.application.dto;

public record LoginRequest(
        String email,
        String password
) {
}
