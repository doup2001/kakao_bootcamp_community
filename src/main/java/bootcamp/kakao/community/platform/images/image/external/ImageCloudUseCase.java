package bootcamp.kakao.community.platform.images.image.external;

import bootcamp.kakao.community.platform.images.image.application.dto.ImageResponse;

import java.io.IOException;
import java.util.List;

public interface ImageCloudUseCase {

    /// 파일 저장을 위한 presignedURL 제공
    ImageResponse getUploadPresignedURL(String file) throws IOException;

    /// 파일 저장을 위한 presignedURL 제공
    List<ImageResponse> getUploadPresignedURL(List<String> files) throws IOException;

    /// 파일 삭제
    void deleteFile(String file) throws IOException;

    /// 파일 삭제
    void deleteFile(List<String> files) throws IOException;

}
