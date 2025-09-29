package bootcamp.kakao.community.platform.posts.comment.domain.repository;

import bootcamp.kakao.community.platform.posts.comment.domain.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
