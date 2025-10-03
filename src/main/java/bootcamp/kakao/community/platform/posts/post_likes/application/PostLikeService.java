package bootcamp.kakao.community.platform.posts.post_likes.application;

import bootcamp.kakao.community.platform.posts.post.domain.entity.Post;
import bootcamp.kakao.community.platform.posts.post.domain.repository.PostRepository;
import bootcamp.kakao.community.platform.posts.post_likes.application.dto.PostLikeRequest;
import bootcamp.kakao.community.platform.posts.post_likes.domain.entity.PostLike;
import bootcamp.kakao.community.platform.posts.post_likes.domain.repository.PostLikeRepository;
import bootcamp.kakao.community.platform.user.application.UserUseCase;
import bootcamp.kakao.community.platform.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostLikeService implements PostLikeUseCase {

    /// 의존성
    private final PostLikeRepository repository;

    /// 외부 의존성
    private final PostRepository postRepository;
    private final UserUseCase userService;

    @Override
    @Transactional
    public void like(PostLikeRequest request, Long userId) {

        /// 존재하는 유저인 지 예외처리
        User user = loadUser(userId);

        /// 존재하는 게시글인 지 예외처리
        Post post = loadPost(request.postId());

        /// 객체 생성 및 저장
        PostLike reqLike = PostLike.of(user, post);
        repository.save(reqLike);

    }

    @Override
    @Transactional
    public void unlike(Long postId, Long userId) {

        /// 존재하는 유저인 지 예외처리
        User user = loadUser(userId);

        /// 헤당 유저가 좋아요를 눌렀었는지 체크
        Post post = loadPost(postId);

        Optional<PostLike> optionalPostLike = repository.findByUserAndPost(user, post);
        if (optionalPostLike.isEmpty()) {
            /// 없다면 예외처리
            throw new IllegalStateException("좋아요를 누르지 않았기에 취소가 불가능합니다.");
        }

        /// 삭제 처리 (바로 삭제)
        repository.delete(optionalPostLike.get());
    }

    /**
     * 예외처리가 된 값들로 처리하는 서비스 로직
     */
    @Override
    @Transactional(readOnly = true)
    public boolean isLiked(Long postId, Long userId) {

        Optional<PostLike> optionalPostLike = repository.findByUser_IdAndPost_Id(userId, postId);

        if (optionalPostLike.isEmpty()) {
            /// 없다면 fasle
            return false;
        } else {
            /// 있다면 true
            return true;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Post> loadLikedPosts(Long userId) {

        /// UserId 값으로 조회 (인덱스 처리)
        List<PostLike> likes = repository.findByUser_Id(userId);

        /// N+1 문제 발생
        /// TODO! fetch Join 으로 고치기
        return likes.stream()
                .map(PostLike::getPost)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> loadLikedUsers(Long postId) {

        /// PostId 값으로 조회 (인덱스 처리)
        List<PostLike> likes = repository.findByPost_Id(postId);

        /// N+1 문제 발생
        /// TODO! fetch Join 으로 고치기
        return likes.stream()
                .map(PostLike::getUser)
                .toList();
    }


    private Post loadPost(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new NoSuchElementException("해당 아이디가 존재하는 게시글이 없습니다."));
    }

    private User loadUser(Long userId) {
        return userService.loadUser(userId);
    }

}
