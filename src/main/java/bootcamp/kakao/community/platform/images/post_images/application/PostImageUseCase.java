package bootcamp.kakao.community.platform.images.post_images.application;

import bootcamp.kakao.community.platform.images.image.domain.entity.Image;
import bootcamp.kakao.community.platform.images.post_images.domain.entity.PostImage;
import bootcamp.kakao.community.platform.posts.post.domain.entity.Post;
import java.util.List;

public interface PostImageUseCase {

    /// 하나의 이미지를 저장하는 로직
    PostImage savePostImage(Post post, Image image);

    /// 여러개의 이미지를 저장하는 로직
    List<PostImage> savePostImage(Post post, List<Image> image);

    /// 게시글에 따른 이미지 조회
    List<PostImage> loadPostImages(Post post);

}
