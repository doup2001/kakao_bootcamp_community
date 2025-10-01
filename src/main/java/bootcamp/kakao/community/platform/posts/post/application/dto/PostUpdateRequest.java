package bootcamp.kakao.community.platform.posts.post.application.dto;

public record PostUpdateRequest(
        Long id,
        Long categoryId,
        String title,
        String content,
        String imageUrl

) {
}
