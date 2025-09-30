package bootcamp.kakao.community.platform.posts.post_likes.application;

import bootcamp.kakao.community.platform.posts.post_likes.domain.repository.PostLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostLikeService implements PostLikeUseCase{

    private final PostLikeRepository repository;

}
