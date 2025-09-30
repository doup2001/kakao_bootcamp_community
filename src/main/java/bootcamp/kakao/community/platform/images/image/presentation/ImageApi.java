package bootcamp.kakao.community.platform.images.image.presentation;

import bootcamp.kakao.community.platform.images.image.application.ImageUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/images")
@RequiredArgsConstructor
public class ImageApi {

    private final ImageUseCase service;


}
