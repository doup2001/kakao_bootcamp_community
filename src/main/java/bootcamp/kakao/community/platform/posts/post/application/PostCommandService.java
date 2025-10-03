package bootcamp.kakao.community.platform.posts.post.application;

import bootcamp.kakao.community.platform.images.image.application.ImageUseCase;
import bootcamp.kakao.community.platform.images.image.domain.entity.Image;
import bootcamp.kakao.community.platform.images.post_images.application.PostImageUseCase;
import bootcamp.kakao.community.platform.posts.category.application.CategoryUseCase;
import bootcamp.kakao.community.platform.posts.category.domain.entity.Category;
import bootcamp.kakao.community.platform.posts.post.application.dto.PostRequest;
import bootcamp.kakao.community.platform.posts.post.application.dto.PostUpdateRequest;
import bootcamp.kakao.community.platform.posts.post.domain.entity.Post;
import bootcamp.kakao.community.platform.posts.post.domain.repository.PostRepository;
import bootcamp.kakao.community.platform.user.application.UserUseCase;
import bootcamp.kakao.community.platform.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class PostCommandService implements PostCommandUseCase{

    private final PostRepository repository;

    /// 의존성
    private final UserUseCase userService;
    private final CategoryUseCase categoryService;

    /// 이미지
    private final ImageUseCase imageService;
    private final PostImageUseCase postImageService;

    /// 게시글 작성, 다중 이미지 고려해서 작성
    @Override
    @Transactional
    public void create(PostRequest req, Long userId) {

        /// 카테고리 예외처리
        Category category = loadCategory(req.categoryId());

        /// 유저 예외처리
        User user = loadUser(userId);

        /// 요청한 실제 이미지가 있는지, 불러오기 (영속성 컨테이너)
        List<Image> image = imageService.getImage(req.imageUrls());

        String thumbnailUrl = null;

        /// 존재한다면
        if (!image.isEmpty()) {
            /// 썸네일 추출
            Image thumbnailImage = image.get(0);
            thumbnailUrl = thumbnailImage.getUrl();
        }

        /// 게시글 객체 생성 및 저장 (영속성 컨테이너)
        var reqPost = Post.of(user, category, req.title(), req.content(), thumbnailUrl);
        Post post = repository.save(reqPost);

        /// 이미지 저장 (영속성 컨테이너에 저장하기), 생성과 함께 Image의 사용이 확정된다.
        postImageService.savePostImage(post, image);

    }


    /// 게시글 수정
    /// 더티체킹으로 실행
    @Override
    @Transactional
    public void update(PostUpdateRequest req, Long userId) {

        /// 게시글 상세 조회 (영속성 컨테이너에 스냅샷)
        Post post = loadPost(req.id());

        /// 유저 예외 처리
        User user = loadUser(userId);

        /// 동일 유저인지 체크
        if (!post.getUser().getId().equals(user.getId())){
            throw new AccessDeniedException("수정할 권한이 없습니다.");
        }

        /// 게시글 수정
        post.update(req.title(), req.content());

    }

    /// 게시글 소프트삭제
    /// 더티체킹으로 실행
    @Override
    @Transactional
    public void delete(Long postId, Long userId) {

        /// 게시글 상세 조회 (영속성 컨테이너에 스냅샷)
        Post post = loadPost(postId);

        /// 유저 예외 처리 (softDELETE 여부 체크)
        User user = loadUser(userId);

        /// 동일 유저인지 체크
        if (!post.getUser().getId().equals(user.getId())) {
            throw new AccessDeniedException("삭제할 권한이 없습니다.");
        }

        /// 게시글 수정
        post.delete();

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
