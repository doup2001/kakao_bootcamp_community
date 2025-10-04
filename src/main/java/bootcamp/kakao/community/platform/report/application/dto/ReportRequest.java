package bootcamp.kakao.community.platform.report.application.dto;

import bootcamp.kakao.community.platform.report.domain.entity.ReportType;

public record ReportRequest(
        ReportType type,
        Long contentId,
        String reason
) {

}
