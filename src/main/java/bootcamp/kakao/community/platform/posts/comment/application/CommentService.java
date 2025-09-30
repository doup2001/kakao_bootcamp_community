package bootcamp.kakao.community.platform.posts.comment.application;

import bootcamp.kakao.community.platform.posts.comment.domain.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService implements CommentUseCase{

    private final CommentRepository repository;
}
