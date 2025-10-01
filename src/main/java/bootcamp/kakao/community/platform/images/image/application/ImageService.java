package bootcamp.kakao.community.platform.images.image.application;

import bootcamp.kakao.community.platform.images.image.application.dto.ImageRequest;
import bootcamp.kakao.community.platform.images.image.application.dto.ImageResponse;
import bootcamp.kakao.community.platform.images.image.domain.entity.Image;
import bootcamp.kakao.community.platform.images.image.domain.repository.ImageRepository;
import bootcamp.kakao.community.platform.images.image.external.ImageCloudUseCase;
import bootcamp.kakao.community.platform.user.application.UserUseCase;
import bootcamp.kakao.community.platform.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ImageService implements ImageUseCase {

    private final ImageCloudUseCase cloudService;
    private final ImageRepository repository;

    /// 의존성
    private final UserUseCase userService;

    /// 게시글 이미지 처리
    @Override
    @Transactional
    public ImageResponse upload(ImageRequest req, Long userId) throws IOException {

        /// 요청한 유저
        User user = userService.getUser(userId).orElseThrow(NoSuchElementException::new);

        /// 클라우드 요청
        ImageResponse presignedURL = cloudService.getUploadPresignedURL(req.file());

        /// 객체 저장
        Image image = Image.of(user, req.file(), presignedURL.preSignedUrl());
        repository.save(image);

        return presignedURL;
    }

    /// 회원가입을 위한 임시 사용
    @Override
    @Transactional
    public ImageResponse uploadTemporaryImage(ImageRequest req) throws IOException {

        /// 클라우드 요청
        ImageResponse presignedURL = cloudService.getUploadPresignedURL(req.file());

        /// 객체 저장
        Image image = Image.temporaryOf(req.file(), presignedURL.preSignedUrl());
        repository.save(image);

        return presignedURL;
    }

    /// URL 바탕으로 이미지 객체 조회하기
    @Override
    @Transactional(readOnly = true)
    public Image getImage(String url) {

        return repository.findByUrl(url)
                .orElseThrow(NoSuchElementException::new);
    }

}
