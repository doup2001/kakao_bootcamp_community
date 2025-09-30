package bootcamp.kakao.community.platform.posts.post.presentation;

import bootcamp.kakao.community.platform.posts.post.application.PostUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/posts")
@RequiredArgsConstructor
public class PostApi {

    private final PostUseCase service;

}
