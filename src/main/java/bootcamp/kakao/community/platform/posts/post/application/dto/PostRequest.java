package bootcamp.kakao.community.platform.posts.post.application.dto;

public record PostRequest(
        Long categoryId,
        String title,
        String content,
        String imageUrl
) {
}
