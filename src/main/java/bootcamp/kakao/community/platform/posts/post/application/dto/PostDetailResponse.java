package bootcamp.kakao.community.platform.posts.post.application.dto;

import bootcamp.kakao.community.platform.images.post_images.application.dto.PostImageResponse;
import bootcamp.kakao.community.platform.images.post_images.domain.entity.PostImage;
import bootcamp.kakao.community.platform.posts.post.domain.entity.Post;
import bootcamp.kakao.community.platform.user.application.dto.UserResponse;
import lombok.Builder;
import java.util.*;

@Builder
public record PostDetailResponse(
        UserResponse user,
        String category,
        String title,
        String content,
        int likeCount,
        int viewCount,
        int commentCount,
        List<PostImageResponse> image,
        String createdAt,
        String updatedAt
) {

    /// 정적 팩토리 메서드
    public static PostDetailResponse from(Post post) {

        /// 정보 조회
        var stat = post.getPostStat();

        return PostDetailResponse.builder()
                .user(UserResponse.from(post.getUser()))
                .category(post.getCategory().getName())
                .title(post.getTitle())
                .content(post.getContent())
                .likeCount(stat.getLikeCount())
                .viewCount(stat.getViewCount())
                .commentCount(stat.getCommentCount())
                .image(null)
                .createdAt(post.getCreatedDate().toString())
                .updatedAt(post.getModifiedDate().toString())
                .build();
    }

    /// 정적 팩토리 메서드
    public static PostDetailResponse from(Post post, List<PostImage> imageList) {

        /// 정보 조회
        var stat = post.getPostStat();

        return PostDetailResponse.builder()
                .user(UserResponse.from(post.getUser()))
                .category(post.getCategory().getName())
                .title(post.getTitle())
                .content(post.getContent())
                .likeCount(stat.getLikeCount())
                .viewCount(stat.getViewCount())
                .commentCount(stat.getCommentCount())
                .image(PostImageResponse.from(imageList))
                .createdAt(post.getCreatedDate().toString())
                .updatedAt(post.getModifiedDate().toString())
                .build();
    }



}
