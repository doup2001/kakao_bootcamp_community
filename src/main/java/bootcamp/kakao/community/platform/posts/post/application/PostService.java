package bootcamp.kakao.community.platform.posts.post.application;

import bootcamp.kakao.community.common.response.paging.SliceRequest;
import bootcamp.kakao.community.common.response.paging.SliceResponse;
import bootcamp.kakao.community.platform.images.image.application.ImageUseCase;
import bootcamp.kakao.community.platform.images.image.domain.entity.Image;
import bootcamp.kakao.community.platform.images.post_images.application.PostImageUseCase;
import bootcamp.kakao.community.platform.images.post_images.domain.entity.PostImage;
import bootcamp.kakao.community.platform.posts.category.application.CategoryUseCase;
import bootcamp.kakao.community.platform.posts.category.domain.entity.Category;
import bootcamp.kakao.community.platform.posts.post.application.dto.PostDetailResponse;
import bootcamp.kakao.community.platform.posts.post.application.dto.PostListResponse;
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
public class PostService implements PostUseCase{

    private final PostRepository repository;

    /// 의존성
    private final UserUseCase userService;
    private final CategoryUseCase categoryService;

    /// 이미지
    private final ImageUseCase imageService;
    private final PostImageUseCase postImageService;

    /// 게시글 작성
    @Override
    @Transactional
    public void create(PostRequest req, Long userId) {

        /// 카테고리 예외처리
        Category category = categoryService.getCategory(req.categoryId())
                .orElseThrow(NoSuchElementException::new);

        /// 유저 예외처리
        User user = loadUser(userId);

        /// 게시글 객체 생성 및 저장 (영속성 컨테이너)
        var reqPost = Post.of(user, category, req.title(), req.content());
        Post post = repository.save(reqPost);

        /// 해당 이미지 불러오기 (영속성 컨테이너)
        Image image = imageService.getImage(req.imageUrl());

        /// 이미지 저장 (영속성 컨테이너)
        PostImage postImage = postImageService.savePostImage(post, image);

        /// 사용중인 이미지로 체크 (더티체킹)
        postImage.getImage().confirm();

    }


    /// 게시글 목록 조회
    @Override
    @Transactional(readOnly = true)
    public SliceResponse<PostListResponse> getPosts(SliceRequest req) {
        return null;
    }

    /// 게시글 상세 조회
    @Override
    @Transactional(readOnly = true)
    public PostDetailResponse getPost(Long postId) {

        /// 게시글 상세 조회
        Post post = loadPost(postId);

        /// 게시글 이미지에서 조회하기
        List<PostImage> images = postImageService.loadPostImages(post);

        /// 응답
        return PostDetailResponse.from(post, images);

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
                .orElseThrow(NoSuchElementException::new);
    }

    private User loadUser(Long userId) {
        return userService.getUser(userId)
                .orElseThrow(NoSuchElementException::new);
    }
}
