package bootcamp.kakao.community.platform.posts.comment.domain.repository;

import bootcamp.kakao.community.common.response.paging.SliceRequest;
import bootcamp.kakao.community.platform.posts.comment.domain.entity.Comment;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryCustomImpl implements CommentRepositoryCustom {

    private JPAQueryFactory queryFactory;

    @Override
    public Slice<Comment> findCommentsByCursor(SliceRequest sliceRequest, Long postId, boolean deleted) {
        return null;
    }
}
