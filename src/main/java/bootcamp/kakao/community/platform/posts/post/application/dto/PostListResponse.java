package bootcamp.kakao.community.platform.posts.post.application.dto;

import bootcamp.kakao.community.platform.user.application.dto.UserResponse;

public record PostListResponse(
        UserResponse user,
        String category,
        String title,
        String content,
        int viewCount,
        int commentCount,
        String createdAt,
        String updatedAt
) {
}
