package bootcamp.kakao.community.platform.posts.post.application;

import bootcamp.kakao.community.platform.posts.post.domain.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService implements PostUseCase{

    private final PostRepository repository;
}
