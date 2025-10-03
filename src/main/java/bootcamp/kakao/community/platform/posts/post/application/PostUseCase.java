package bootcamp.kakao.community.platform.posts.post.application;

import bootcamp.kakao.community.common.response.paging.SliceRequest;
import bootcamp.kakao.community.common.response.paging.SliceResponse;
import bootcamp.kakao.community.platform.posts.post.application.dto.PostDetailResponse;
import bootcamp.kakao.community.platform.posts.post.application.dto.PostRequest;
import bootcamp.kakao.community.platform.posts.post.application.dto.PostListResponse;
import bootcamp.kakao.community.platform.posts.post.application.dto.PostUpdateRequest;
import bootcamp.kakao.community.platform.posts.post.domain.entity.Post;

public interface PostUseCase {

    /// 글 작성
    void create(PostRequest req, Long userId);

    /// 목록 조회하기 (카테고리 기반)
    SliceResponse<PostListResponse> getPosts(SliceRequest req, Long categoryId);

    /// 목록 조회하기 (인기 기반)
    SliceResponse<PostListResponse> getFavoritePosts(SliceRequest req);

    /// 상세 조회하기
    PostDetailResponse getPost(Long postId, Long userId);

    /// 게시글 수정하기
    void update(PostUpdateRequest req, Long userId);

    /// 게시글 삭제하기
    void delete(Long postId, Long userId);

    /// 외부사용
    Post loadPost(Long postId);

}
