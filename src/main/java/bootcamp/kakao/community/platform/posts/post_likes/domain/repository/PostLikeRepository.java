package bootcamp.kakao.community.platform.posts.post_likes.domain.repository;

import bootcamp.kakao.community.platform.posts.post.domain.entity.Post;
import bootcamp.kakao.community.platform.posts.post_likes.domain.entity.PostLike;
import bootcamp.kakao.community.platform.posts.post_likes.domain.entity.PostLikeId;
import bootcamp.kakao.community.platform.user.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

public interface PostLikeRepository extends JpaRepository<PostLike, PostLikeId> {

    Optional<PostLike> findByUserAndPost(User user, Post post);

    Optional<PostLike> findByUser_IdAndPost_Id(Long userId, Long postId);

    List<PostLike> findByUser_Id(Long userId);

    List<PostLike> findByPost_Id(Long postId);
}
