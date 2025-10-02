package bootcamp.kakao.community.platform.posts.post.application.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

/**
 * 이미지 생성 요청 DTO
 * @param categoryId    카테고리 ID
 * @param title         제목
 * @param content       내용
 * @param imageUrls     이미지 URLs
 */
public record PostRequest(

        @NotNull(message = "카테고리없이 게시글을 작성할 수 없습니다.")
        Long categoryId,

        @Size(max = 26, message = "제목의 길이가 26자를 넘습니다.")
        @NotNull(message = "제목없이 게시글을 작성할 수 없습니다.")
        String title,

        @NotNull(message = "내용없이 게시글을 작성할 수 없습니다.")
        String content,

        List<String> imageUrls
) {
}
