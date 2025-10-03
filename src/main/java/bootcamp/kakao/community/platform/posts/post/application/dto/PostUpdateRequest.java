package bootcamp.kakao.community.platform.posts.post.application.dto;

import java.util.List;

public record PostUpdateRequest(
        Long id,
        Long categoryId,
        String title,
        String content,
        List<String> imageUrls
) {
}
