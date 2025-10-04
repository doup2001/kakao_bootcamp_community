package bootcamp.kakao.community.platform.report.application;

import bootcamp.kakao.community.platform.report.application.dto.ReportRequest;

public interface ReportUseCase {

    /// 신고하기
    void createReport(ReportRequest request, Long userId);

}
