package bootcamp.kakao.community.platform.posts.post.application;

import bootcamp.kakao.community.common.response.paging.SliceRequest;
import bootcamp.kakao.community.common.response.paging.SliceResponse;
import bootcamp.kakao.community.common.util.KeyUtil;
import bootcamp.kakao.community.platform.images.post_images.application.PostImageUseCase;
import bootcamp.kakao.community.platform.images.post_images.domain.entity.PostImage;
import bootcamp.kakao.community.platform.posts.category.application.CategoryUseCase;
import bootcamp.kakao.community.platform.posts.category.domain.entity.Category;
import bootcamp.kakao.community.platform.posts.post.application.dto.PostDetailResponse;
import bootcamp.kakao.community.platform.posts.post.application.dto.PostListResponse;
import bootcamp.kakao.community.platform.posts.post.domain.entity.Post;
import bootcamp.kakao.community.platform.posts.post.domain.repository.PostRepository;
import bootcamp.kakao.community.platform.posts.post_likes.application.PostLikeUseCase;
import bootcamp.kakao.community.platform.user.application.UserUseCase;
import bootcamp.kakao.community.platform.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class PostQueryService implements PostQueryUseCase{

    private final PostRepository repository;
    private final RedisTemplate<String, Object> redisTemplate;

    /// 의존성
    private final UserUseCase userService;
    private final CategoryUseCase categoryService;

    /// 이미지
    private final PostImageUseCase postImageService;

    /// 좋아요 여부 파악
    private final PostLikeUseCase likeUseCase;

    /// 게시글 목록 조회
    @Override
    @Transactional(readOnly = true)
    public SliceResponse<PostListResponse> getPosts(SliceRequest req, Long categoryId) {

        /// 카테고리 예외처리
        Category category = loadCategory(categoryId);

        /// 요청에 따라서 목록 조회
        Slice<Post> posts = repository.findPostsByCursor(req, category.getName(), false);

        /// DTO 변화
        Slice<PostListResponse> response = PostListResponse.from(posts);
        return SliceResponse.from(response);
    }

    /// 인기 게시글 목록 조회
    @Override
    @Transactional(readOnly = true)
    public SliceResponse<PostListResponse> getFavoritePosts(SliceRequest req) {
        return null;
    }

    /// 게시글 상세 조회
    /// 조회수가 상승해야한다.
    @Override
    @Transactional(readOnly = true)
    public PostDetailResponse getPost(Long postId, Long userId) {

        /// 게시글 DB 조회
        Post post = loadPost(postId);

        /// 게시글 이미지 조회하기
        List<PostImage> images = postImageService.loadPostImages(post);

        /// 조회했기에, 레디스에서 조회수 증가시키기
        String redisKey = KeyUtil.getPostView(post.getId());
        redisTemplate.opsForValue().increment(redisKey, 1);

        /// 레디스에서 조회 수, 댓글 수,좋아요 수를 가져와야한다.
        // TODO! 레디스에서 값 가져오기

        /// 비회원이 조회했다면
        if (userId == null) {

            /// 게시글의 정보만 전달하면 된다.
            return PostDetailResponse.from(post, images);

        } else {

            /// 회원이 조회했다면,
            User user = loadUser(userId);

            /// 좋아요 여부 조회
            boolean liked = likeUseCase.isLiked(postId, userId);

            /// 편집 가능 여부 조회
            /// ID는 프록시 객체이기에 getUser의 Id를 해도 지연로딩이 발생하지않음!, 성능만 문제 X
            boolean editable = post.getUser().getId().equals(user.getId());

            /// 응답
            return PostDetailResponse.from(post, images, liked, editable);
        }
    }


    // =================
    //  내부 로직
    // =================
    @Transactional(readOnly = true)
    protected Post loadPost(Long postId) {
        return repository.findByIdAndDeletedIsFalse(postId)
                .orElseThrow(() -> new NoSuchElementException("해당 아이디가 존재하는 게시글이 없습니다."));
    }

    private User loadUser(Long userId) {
        return userService.loadUser(userId);
    }

    private Category loadCategory(Long categoryId) {
        return categoryService.loadCategory(categoryId);
    }

}
