package bootcamp.kakao.community.platform.images.image.application;

import bootcamp.kakao.community.platform.images.image.application.dto.ImageRequest;
import bootcamp.kakao.community.platform.images.image.application.dto.ImageResponse;

import java.io.IOException;

public interface ImageUseCase {

    /// 사진 저장하기
    ImageResponse upload(ImageRequest req, Long userId) throws IOException;

    /// 회원가입을 위한 임시 저장소
    ImageResponse uploadTemporaryImage(ImageRequest req) throws IOException;

}
