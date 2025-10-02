package bootcamp.kakao.community.platform.images.post_images.domain.entity;

import bootcamp.kakao.community.platform.BaseTimeEntity;
import bootcamp.kakao.community.platform.images.image.domain.entity.Image;
import bootcamp.kakao.community.platform.posts.post.domain.entity.Post;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "posts_images")
public class PostImage extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id", nullable = false)
    private Image image;

    @Column(nullable = false)
    private int ord = 0;

    /// 빌더 생성자
    @Builder
    protected PostImage(Post post, Image image, int ord) {
        this.post = post;
        this.image = image;
        this.ord = ord;
    }

    /// 정적 팩토리 메서드
    public static PostImage of(Post post, Image image, int ord) {

        /// 게시글 이미지로 들어가게 되면, 사용 확정
        image.confirm();

        return PostImage.builder()
                .post(post)
                .image(image)
                .ord(ord)
                .build();
    }

}
