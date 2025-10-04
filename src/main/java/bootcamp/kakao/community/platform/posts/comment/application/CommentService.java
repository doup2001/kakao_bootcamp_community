package bootcamp.kakao.community.platform.posts.comment.application;

import bootcamp.kakao.community.platform.posts.comment.domain.entity.Comment;
import bootcamp.kakao.community.platform.posts.comment.domain.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CommentService implements CommentUseCase{

    private final CommentRepository repository;

    @Override
    @Transactional(readOnly = true)
    public Comment loadComment(Long commentId) {
        return repository.findById(commentId)
                .orElseThrow(() -> new NoSuchElementException("해당 아이디를 가진 댓글은 존재하지않습니다."));
    }
}
