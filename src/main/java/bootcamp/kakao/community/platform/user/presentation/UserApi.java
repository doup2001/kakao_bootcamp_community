package bootcamp.kakao.community.platform.user.presentation;

import bootcamp.kakao.community.platform.user.application.UserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UserApi {

    private final UserUseCase service;


}
