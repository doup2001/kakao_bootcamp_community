package bootcamp.kakao.community.platform.posts.post.application.dto;

import java.util.List;

/**
 * 이미지 생성 요청 DTO
 * @param categoryId    카테고리 ID
 * @param title         제목
 * @param content       내용
 * @param imageUrls     이미지 URLs
 */
public record PostRequest(
        Long categoryId,
        String title,
        String content,
        List<String> imageUrls
) {
}
