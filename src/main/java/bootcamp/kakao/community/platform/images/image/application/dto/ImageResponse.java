package bootcamp.kakao.community.platform.images.image.application.dto;

import lombok.Builder;

/**
 * S3 이미지 DTO
 * @param fileName      원본 파일 이름
 * @param preSignedUrl  저장할 S3 주소
 */
@Builder
public record ImageResponse(
        String fileName,
        String preSignedUrl) {

    /// 정적 팩토리 메서드
    public static ImageResponse from(String fileName, String preSignedUrl) {
        return ImageResponse.builder()
                .fileName(fileName)
                .preSignedUrl(preSignedUrl)
                .build();
    }
}
