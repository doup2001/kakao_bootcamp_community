package bootcamp.kakao.community.platform.posts.category.application.dto;

/**
 * @param parentId  상위 카테고리
 * @param name      카테고리 명
 */
public record CategoryRequest(
        Long parentId,
        String name
) {
}
