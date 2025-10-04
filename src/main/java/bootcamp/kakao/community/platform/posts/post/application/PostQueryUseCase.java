package bootcamp.kakao.community.platform.posts.post.application;

import bootcamp.kakao.community.common.response.paging.SliceRequest;
import bootcamp.kakao.community.common.response.paging.SliceResponse;
import bootcamp.kakao.community.platform.posts.post.application.dto.PostDetailResponse;
import bootcamp.kakao.community.platform.posts.post.application.dto.PostListResponse;

public interface PostQueryUseCase {

    /// 목록 조회하기 (카테고리 기반)
    SliceResponse<PostListResponse> getPosts(SliceRequest req, Long categoryId);

    /// 목록 조회하기 (인기 기반)
    SliceResponse<PostListResponse> getFavoritePosts(SliceRequest req);

    /// 상세 조회하기
    PostDetailResponse getPost(Long postId, Long userId);

}
