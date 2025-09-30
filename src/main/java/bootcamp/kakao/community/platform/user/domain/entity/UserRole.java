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

    @JsonValue
    public String getLabel() {
        return label;
    }

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
