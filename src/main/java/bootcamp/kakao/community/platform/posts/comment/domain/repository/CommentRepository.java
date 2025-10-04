package bootcamp.kakao.community.platform.posts.comment.domain.repository;

import bootcamp.kakao.community.platform.posts.comment.domain.entity.Comment;
import bootcamp.kakao.community.platform.user.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long>, CommentRepositoryCustom {

    /// 유저와 아이디로 조회
    Optional<Comment> findByUserAndId(User user, Long id);
}
