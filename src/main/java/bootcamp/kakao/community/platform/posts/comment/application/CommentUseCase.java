package bootcamp.kakao.community.platform.posts.comment.application;

import bootcamp.kakao.community.platform.posts.comment.domain.entity.Comment;

public interface CommentUseCase {


    /// 외부 사용
    Comment loadComment(Long commentId);
}
