package bootcamp.kakao.community.platform.user.application;

import bootcamp.kakao.community.platform.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserUseCase{

    private final UserRepository userRepository;
}
