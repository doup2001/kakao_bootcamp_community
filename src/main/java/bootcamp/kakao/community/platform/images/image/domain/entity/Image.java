package bootcamp.kakao.community.platform.images.image.domain.entity;

import bootcamp.kakao.community.platform.BaseTimeEntity;
import bootcamp.kakao.community.platform.user.domain.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "images")
public class Image extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String fileName;

    @Column(nullable = false)
    private String url;

    /// 생성자
    @Builder
    protected Image(User user, String fileName, String url) {
        this.user = user;
        this.fileName = fileName;
        this.url = url;
    }

    /// 정적 팩토리 메서드
    public static Image of(User user, String fileName, String url) {
        return Image.builder()
                .user(user)
                .fileName(fileName)
                .url(url)
                .build();
    }


}
