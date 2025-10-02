package bootcamp.kakao.community.platform.posts.post.domain.repository;

import bootcamp.kakao.community.common.response.paging.SliceRequest;
import bootcamp.kakao.community.platform.posts.post.domain.entity.Post;
import org.springframework.data.domain.Slice;

public interface PostRepositoryCustom {

    /**
     * NoOffset을 기반으로 조회한다.
     * @param sliceRequest  슬라이싱 요청
     * @param category      카테고리
     */
    Slice<Post> findPostsByCursor(SliceRequest sliceRequest, String category, boolean deleted);

}
