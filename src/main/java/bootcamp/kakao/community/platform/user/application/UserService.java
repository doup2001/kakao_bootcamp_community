package bootcamp.kakao.community.platform.user.application;

import bootcamp.kakao.community.platform.user.domain.entity.User;
import bootcamp.kakao.community.platform.user.domain.repository.UserRepository;
import bootcamp.kakao.community.platform.user.application.dto.SignUpRequest;
import bootcamp.kakao.community.security.jwt.application.JwtProvider;
import bootcamp.kakao.community.security.jwt.application.dto.JwtTokenResponse;
import bootcamp.kakao.community.security.jwt.application.dto.JwtTokenRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserUseCase{

    private final UserRepository repository;
    private final BCryptPasswordEncoder passwordEncoder;

    private final JwtProvider jwtProvider;

    /// 회원가입, 회원가입 후 바로 이용가능하도록 토큰 발급
    @Override
    @Transactional
    public JwtTokenResponse signUp(SignUpRequest request, String deviceType) {

        /// 이메일 중복체크 및 예외처리
        boolean duplicateEmail = checkDuplicateEmail(request.email());
        if (duplicateEmail) {
            throw new IllegalArgumentException("이메일이 중복되는 문제입니다.");
        }

        /// 닉네임 중복체크 및 예외처리
        boolean duplicateNickName = checkDuplicateNickName(request.nickname());
        if (duplicateNickName) {
            throw new IllegalArgumentException("닉네임이 중복되는 문제입니다.");
        }

        /// 요청한 비밀번호 값이 같은지
        if (!request.confirmPassword().equals(request.password())) {
            throw new IllegalArgumentException("패스워드가 일치하지 않는 문제입니다.");
        }

        /// 객체 생성 후, 저장
        var requestUser = User.of(request.name(), request.imageUrl(), request.nickname(), request.email(), passwordEncoder.encode(request.password()));
        User user = repository.save(requestUser);

        /// 로그인했다면, JWT 발급하기
        var jwtRequest = JwtTokenRequest.from(user);

        String accessToken = jwtProvider.createAccessToken(jwtRequest);
        String refreshToken = jwtProvider.createRefreshToken(deviceType, jwtRequest);

        return JwtTokenResponse.of(accessToken, refreshToken);

    }

    /// 회원탈퇴
    @Override
    @Transactional
    public void withdraw(Long userId) {

        /// 영속성 컨테이너에 값 불러오기
        User user = repository.findByIdAndDeletedIsFalse(userId)
                .orElseThrow(() -> new NoSuchElementException("해당 유저는 없습니다."));

        /// 더티체킹으로 값 수정하기
        user.delete();
    }

    /// 이메일 중복 여부
    @Override
    @Transactional(readOnly = true)
    public boolean checkDuplicateEmail(String email) {
        return repository.existsByEmail(email);
    }

    /// 닉네임 중복 여부
    @Override
    @Transactional(readOnly = true)
    public boolean checkDuplicateNickName(String nickName) {
        return repository.existsByNickname(nickName);
    }


    // =================
    //  외부 조회 로직
    // =================
    @Override
    @Transactional(readOnly = true)
    public User loadUser(Long userId) {
        return repository.findByIdAndDeletedIsFalse(userId)
                .orElseThrow(() -> new NoSuchElementException("해당 아이디가 존재하는 유저가 없습니다."));
    }

}
