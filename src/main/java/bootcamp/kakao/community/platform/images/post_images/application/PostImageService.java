package bootcamp.kakao.community.platform.images.post_images.application;

import bootcamp.kakao.community.platform.images.image.domain.entity.Image;
import bootcamp.kakao.community.platform.images.post_images.domain.entity.PostImage;
import bootcamp.kakao.community.platform.images.post_images.domain.repository.PostImageRepository;
import bootcamp.kakao.community.platform.posts.post.domain.entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class PostImageService implements PostImageUseCase {

    private final PostImageRepository repository;

    /// 게시글에 이미지 저장
    @Override
    public PostImage savePostImage(Post post, Image image) {

        /// 게시글 이미지 저장하기
        PostImage postImage = PostImage.of(post, image, 0);

        /// 저장하기
        return repository.save(postImage);
    }

    /// 게시글에 여러개 이미지 저장
    @Override
    public List<PostImage> savePostImage(Post post, List<Image> images) {

        /// List 형식 극복하기
        List<PostImage> postImages = IntStream.range(0, images.size())
                .mapToObj(i -> PostImage.of(post, images.get(i), i))
                .toList();

        /// 저장하기
        return repository.saveAll(postImages);

    }

    /// 게시글의 여러개 이미지 조회
    @Override
    public List<PostImage> loadPostImages(Post post) {
        return repository.findByPost(post);
    }

}
