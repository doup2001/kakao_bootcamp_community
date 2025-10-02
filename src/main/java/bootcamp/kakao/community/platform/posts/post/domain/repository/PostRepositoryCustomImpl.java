package bootcamp.kakao.community.platform.posts.post.domain.repository;

import bootcamp.kakao.community.common.response.paging.SliceRequest;
import bootcamp.kakao.community.platform.posts.post.domain.entity.Post;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static bootcamp.kakao.community.platform.posts.post.domain.entity.QPost.post;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostRepositoryCustomImpl implements PostRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    /// NoOffset 으로 구현
    @Override
    public Slice<Post> findPostsByCursor(SliceRequest sliceRequest, String category, boolean deleted) {

        /// 해당하는 만큼 조회
        List<Post> fetch = queryFactory
                .selectFrom(post)
                .where(
                        ltLastId(sliceRequest.lastId()),
                        eqCategory(category),
                        eqDeleted(deleted)
                )
                .orderBy(post.id.desc())
                .limit(sliceRequest.offSet() + 1) // hasNext 확인용 +1
                .fetch();


        /// SliceImpl 제작
        boolean hasNext = false;
        if (fetch.size() > sliceRequest.offSet()) {

            /// 조회했던 값은 삭제
            fetch.remove(sliceRequest.offSet());
            hasNext = true;
        }

        /// Pageable 제작
        Pageable pageable = PageRequest.of(0, sliceRequest.offSet());

        /// 응답 생성
        return new SliceImpl<>(fetch, pageable, hasNext);

    }

    /// id < 첫 번째 조회에서는 파라미터를 사용하지 않기 위한 동적 쿼리
    private BooleanExpression ltLastId(Long lastId) {

        if (lastId == null) {
            /// BooleanExpression 자리에 null이 반환되면 조건문에서 자동을 제외된다.
            return null;
        }
        return post.id.lt(lastId);
    }

    /// 카테고리
    private BooleanExpression eqCategory(String category) {

        if (category == null) {
            /// BooleanExpression 자리에 null이 반환되면 조건문에서 자동을 제외된다.
            return null;
        }
        return post.category.name.eq(category);
    }

    private BooleanExpression eqDeleted(boolean deleted) {

        return post.deleted.eq(deleted);
    }

}
