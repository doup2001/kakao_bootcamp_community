package bootcamp.kakao.community.platform.posts.comment.presentation;

import bootcamp.kakao.community.platform.posts.comment.application.CommentUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/comment")
public class CommentApi {

    private final CommentUseCase service;
}
