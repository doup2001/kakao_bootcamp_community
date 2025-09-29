package bootcamp.kakao.community.platform.report.domain.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ReportType {

    USER("유저"),
    POST("게시글"),
    COMMENT("댓글");

    private final String label;

    @JsonValue
    public String getLabel() {
        return label;
    }

    @JsonCreator
    public static ReportType fromLabel(String label) {
        for (ReportType type : ReportType.values()) {
            if (type.getLabel().equals(label)) {
                return type;
            }
        }
        return null;
    }

}
