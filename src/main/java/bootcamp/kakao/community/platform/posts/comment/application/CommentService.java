package bootcamp.kakao.community.platform.posts.comment.application;

import bootcamp.kakao.community.common.response.paging.SliceRequest;
import bootcamp.kakao.community.common.response.paging.SliceResponse;
import bootcamp.kakao.community.platform.posts.comment.application.dto.CommentRequest;
import bootcamp.kakao.community.platform.posts.comment.application.dto.CommentResponse;
import bootcamp.kakao.community.platform.posts.comment.application.dto.CommentUpdateRequest;
import bootcamp.kakao.community.platform.posts.comment.domain.entity.Comment;
import bootcamp.kakao.community.platform.posts.comment.domain.repository.CommentRepository;
import bootcamp.kakao.community.platform.posts.post.application.PostQueryUseCase;
import bootcamp.kakao.community.platform.posts.post.domain.entity.Post;
import bootcamp.kakao.community.platform.user.application.UserUseCase;
import bootcamp.kakao.community.platform.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CommentService implements CommentUseCase{

    private final CommentRepository repository;
    private final PostQueryUseCase postService;
    private final UserUseCase userService;

    @Override
    @Transactional
    public void createComment(CommentRequest request, Long userId) {

        /// 유저 예외처리
        User user = userService.loadUser(userId);

        /// 게시글 예외처리
        Post post = postService.loadPost(request.postId());

        Comment parent = null;
        if (request.parentId() != null){
            /// 부모 댓글이 있다면 조회
            parent = loadComment(request.parentId());
        }

        /// 객체 생성
        Comment.of(post, user, parent, request.content());

    }

    @Override
    @Transactional(readOnly = true)
    public SliceResponse<CommentResponse> getComments(SliceRequest request, Long postId) {

        /// 게시글 예외처리
        Post post = postService.loadPost(postId);

        /// 가져오기
        Slice<Comment> comments = repository.findCommentsByCursor(request, post.getId(), false);

        /// 리턴
        Slice<CommentResponse> var = CommentResponse.from(comments);
        return SliceResponse.from(var);
    }

    @Override
    @Transactional(readOnly = true)
    public SliceResponse<CommentResponse> getFavoriteComments(SliceRequest request, Long postId) {
        /// 게시글 예외처리
        Post post = postService.loadPost(postId);

        /// 가져오기
        // TODO 인기 댓글 조회하기

        /// 리턴
        return null;
    }

    @Override
    @Transactional
    public void updateComment(CommentUpdateRequest request, Long userId) {

        /// 유저 예외처리
        User user = userService.loadUser(userId);

        /// 나의 댓글인 지 조회
        /// ID 존재 및 작성 여부를 한번에 파악
        Comment comment = repository.findByUserAndId(user, request.id())
                .orElseThrow(() -> new NoSuchElementException("해당 유저가 수정할 댓글이 없습니다."));

        /// 더티체킹 수정
        comment.update(request.content());

    }


    /// 삭제하기 (더티체킹)
    @Override
    @Transactional
    public void deleteComment(Long commentId, Long userId) {

        /// 유저 예외처리
        User user = userService.loadUser(userId);

        /// ID 존재 및 작성 여부를 한번에 파악 (영속성 컨테이너)
        Comment comment = repository.findByUserAndId(user, commentId)
                .orElseThrow(() -> new NoSuchElementException("해당 유저가 삭제할 댓글이 없습니다."));

        /// 삭제
        comment.delete();

    }

    @Override
    @Transactional(readOnly = true)
    public Comment loadComment(Long commentId) {
        return repository.findById(commentId)
                .orElseThrow(() -> new NoSuchElementException("해당 아이디를 가진 댓글은 존재하지않습니다."));
    }
}

