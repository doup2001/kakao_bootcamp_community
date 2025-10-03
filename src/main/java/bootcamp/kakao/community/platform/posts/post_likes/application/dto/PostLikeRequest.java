package bootcamp.kakao.community.platform.posts.post_likes.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        name = "[요청][좋아요] 게시글 좋아요 Reqeust",
        description = "좋아요 생성을 위한 DTO입니다."
)
public record PostLikeRequest(
        @Schema(description = "게시글 ID", example = "1")
        Long postId
) {
}
