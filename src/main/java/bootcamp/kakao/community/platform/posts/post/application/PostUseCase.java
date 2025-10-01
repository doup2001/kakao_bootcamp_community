package bootcamp.kakao.community.platform.posts.post.application;

import bootcamp.kakao.community.common.response.paging.SliceRequest;
import bootcamp.kakao.community.common.response.paging.SliceResponse;
import bootcamp.kakao.community.platform.posts.post.application.dto.PostDetailResponse;
import bootcamp.kakao.community.platform.posts.post.application.dto.PostRequest;
import bootcamp.kakao.community.platform.posts.post.application.dto.PostListResponse;
import bootcamp.kakao.community.platform.posts.post.application.dto.PostUpdateRequest;

public interface PostUseCase {

    /// 글 작성자
    void create(PostRequest req, Long userId);

    /// 목록 조회하기
    SliceResponse<PostListResponse> getPosts(SliceRequest req);

    /// 상세 조회하기
    PostDetailResponse getPost(Long postId);

    /// 게시글 수정하기
    void update(PostUpdateRequest req, Long userId);

    /// 게시글 삭제하기
    void delete(Long postId, Long userId);

}
