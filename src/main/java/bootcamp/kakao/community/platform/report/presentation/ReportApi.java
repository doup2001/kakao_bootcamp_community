package bootcamp.kakao.community.platform.report.presentation;

import bootcamp.kakao.community.platform.report.application.ReportUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/report")
@RequiredArgsConstructor
public class ReportApi {

    private final ReportUseCase service;
}
