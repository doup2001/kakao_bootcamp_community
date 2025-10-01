package bootcamp.kakao.community.platform.user.domain.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserRole {
    MEMBER("유저"),
    ADMIN("관리자");

    private final String label;

    /// ROLE 가져올 때
    public String getRole() {
        return "ROLE_" + name();
    }

    /// JSON에서 응답
    @JsonValue
    public String getLabel() {
        return label;
    }

    /// JSON에서 생성
    @JsonCreator
    public static UserRole fromLabel(String label) {
        for (UserRole userRole : values()) {
            if (userRole.label.equals(label)) {
                return userRole;
            }
        }
        throw new IllegalArgumentException("Unknown UserRole label: " + label);
    }
}
