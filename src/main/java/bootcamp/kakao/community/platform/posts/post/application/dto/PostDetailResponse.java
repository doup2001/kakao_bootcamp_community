package bootcamp.kakao.community.platform.posts.post.application.dto;

import bootcamp.kakao.community.platform.images.post_images.application.dto.PostImageResponse;
import bootcamp.kakao.community.platform.images.post_images.domain.entity.PostImage;
import bootcamp.kakao.community.platform.posts.post.domain.entity.Post;
import bootcamp.kakao.community.platform.user.application.dto.UserResponse;
import lombok.Builder;
import java.util.*;

/**
 * 게시글 상세조회 DTO
 * @param author            작성자
 * @param category          카테고리
 * @param title             제목
 * @param content           내용
 * @param likeCount         좋아요수
 * @param viewCount         조회수
 * @param commentCount      댓글수
 * @param image             이미지
 * @param liked             좋아요 눌렀는지 여부
 * @param editable          수정/삭제 가능 여부
 * @param createdAt         글 작성 시간
 * @param updatedAt         글 수정 시간
 */
@Builder
public record PostDetailResponse(
        UserResponse author,
        String category,
        String title,
        String content,
        int likeCount,
        int viewCount,
        int commentCount,
        List<PostImageResponse> image,
        boolean liked,
        boolean editable,
        String createdAt,
        String updatedAt
) {

    /// 정적 팩토리 메서드
    public static PostDetailResponse from(Post post) {

        /// 정보 조회
        var stat = post.getPostStat();

        return PostDetailResponse.builder()
                .author(UserResponse.from(post.getUser()))
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
                .author(UserResponse.from(post.getUser()))
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
