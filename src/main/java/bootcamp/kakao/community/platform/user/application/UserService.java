package bootcamp.kakao.community.platform.user.application;

import bootcamp.kakao.community.platform.user.domain.entity.User;
import bootcamp.kakao.community.platform.user.domain.repository.UserRepository;
import bootcamp.kakao.community.platform.user.application.dto.SignUpRequest;
import bootcamp.kakao.community.security.auth.domain.CustomUserDetails;
import bootcamp.kakao.community.security.jwt.application.JwtUseCase;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserService implements UserUseCase{

    private final UserRepository repository;
    private final JwtUseCase tokenService;

    /// 패스워드 암호화
    private final BCryptPasswordEncoder passwordEncoder;

    /// 회원가입
    @Override
    @Transactional
    public void signUp(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, SignUpRequest request) {

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

        /// 객체 생성
        User user = User.of(request.name(), request.imageUrl(), request.nickname(), request.email(), passwordEncoder.encode(request.password()));
        User savedUser = repository.save(user);

        /// 시큐리티에 유저 저장
        CustomUserDetails customUserDetails = CustomUserDetails.of(savedUser);
        Authentication authentication = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        /// 토큰 발급
        tokenService.createAccessToken(httpServletRequest, httpServletResponse, customUserDetails);
    }

    /// 회원탈퇴
    @Override
    @Transactional
    public void withdraw(HttpServletRequest httpServletRequest, HttpServletResponse response, CustomUserDetails customUserDetails) {

        /// 유저 ID
        Long userId = customUserDetails.getId();

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
}
