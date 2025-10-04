package bootcamp.kakao.community.platform.posts.comment.application.dto;

public record CommentUpdateRequest(
        Long id,
        String content
) {
}
