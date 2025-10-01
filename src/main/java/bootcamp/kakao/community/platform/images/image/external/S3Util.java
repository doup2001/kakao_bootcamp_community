package bootcamp.kakao.community.platform.images.image.external;

import bootcamp.kakao.community.common.response.ErrorCode;
import bootcamp.kakao.community.common.util.ImageUtil;
import bootcamp.kakao.community.platform.images.image.application.dto.ImageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

@Component
@RequiredArgsConstructor
public class S3Util implements ImageCloudUseCase {

    @Value("${cloud.aws.S3.bucket}")
    private String bucketName;

    /// PreSignedURL을 위한 s3Presigner
    private final S3Presigner s3Presigner;

    /// 삭제를 위한 S3Client
    private final S3Client s3Client;

    /// 이미지 생성
    private final ImageUtil imageUtil;

    /**
     * S3에 하나의 파일 저장
     * @param file  저장할 이미지 파일 이름
     */
    @Override
    public ImageResponse getUploadPresignedURL(String file) throws IOException {

        /// 파일명 수정
        String key = imageUtil.generateKey(file);

        /// 파일 DTO 생성
        PresignedPutObjectRequest presignedRequest = s3Presigner.presignPutObject(
                req -> req.signatureDuration(Duration.ofMinutes(15)) /// 15분 유효기간
                        .putObjectRequest(
                                PutObjectRequest.builder()
                                        .bucket(bucketName)
                                        .key(key)
                                        .build()
                        )
        );
        String presignedUrl = presignedRequest.url().toString();
        return ImageResponse.from(file, presignedUrl);
    }

    /**
     * S3에 여러개의 파일 저장
     * @param files  저장할 이미지 파일들 이름
     */
    @Override
    public List<ImageResponse> getUploadPresignedURL(List<String> files) {
        return files.stream()
                .map(file -> {
                    try {
                        return getUploadPresignedURL(file);
                    } catch (IOException e) {
                        throw new IllegalStateException(ErrorCode.INTERNAL_S3_URL_ERROR.getMessage());
                    }
                })
                .toList();
    }


    /**
     * 이미지 삭제하기
     * @param fileName  S3에서 삭제할 이미지 이름
     */
    @Override
    public void deleteFile(String fileName) {

        /// 삭제할 오브젝트 생성
        DeleteObjectRequest request = DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .build();


        /// 삭제 요청
        s3Client.deleteObject(request);
    }

    /**
     * 이미지 삭제하기
     * @param fileNames  S3에서 삭제할 이미지 여러 개
     */
    @Override
    public void deleteFile(List<String> fileNames) {

        /// 반복해서 삭제
        for (String fileName : fileNames) {
            deleteFile(fileName);
        }
    }

}
