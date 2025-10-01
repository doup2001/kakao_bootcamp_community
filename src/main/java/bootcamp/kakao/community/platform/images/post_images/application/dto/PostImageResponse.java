package bootcamp.kakao.community.platform.images.post_images.application.dto;

import bootcamp.kakao.community.platform.images.post_images.domain.entity.PostImage;
import lombok.Builder;
import java.util.List;

@Builder
public record PostImageResponse(
        String imageUrl,
        int order
) {

    /// 정적 팩토리 메서드
    public static PostImageResponse from(PostImage postImage) {

        return PostImageResponse.builder()
                .imageUrl(postImage.getImage().getUrl())
                .order(postImage.getOrd())
                .build();
    }


    /// 정적 팩토리 메서드
    public static List<PostImageResponse> from(List<PostImage> postImages) {

        return postImages.stream()
                .map(PostImageResponse::from)
                .toList();
    }

}
