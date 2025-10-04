package bootcamp.kakao.community.platform.posts.post_likes.application;

import bootcamp.kakao.community.platform.posts.post.domain.entity.Post;
import bootcamp.kakao.community.platform.posts.post_likes.application.dto.PostLikeRequest;
import bootcamp.kakao.community.platform.user.domain.entity.User;

import java.util.List;

public interface PostLikeUseCase {

    /// 좋아요 누르기
    void like(PostLikeRequest request, Long userId);

    /// 좋아요 취소하기
    void unlike(Long postId, Long userId);

    // =================
    //  외부 사용 로직
    // =================

    /// 나의 좋아요 여부 파악하기
    boolean isLiked(Long postId, Long userId);

    /// 나의 좋아요 목록 조회하기
    List<Post> loadLikedPosts(Long userId);

    /// 좋아요한 유저들 목록 조회하기
    List<User> loadLikedUsers(Long postId);

}
