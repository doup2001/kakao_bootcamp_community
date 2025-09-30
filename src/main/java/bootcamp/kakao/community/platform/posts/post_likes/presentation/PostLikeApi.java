package bootcamp.kakao.community.platform.posts.post_likes.presentation;

import bootcamp.kakao.community.platform.posts.post_likes.application.PostLikeUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/posts/likes")
@RequiredArgsConstructor
public class PostLikeApi {

    private final PostLikeUseCase service;

}
