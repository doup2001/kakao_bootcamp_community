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
    @JoinColumn(name = "user_id", nullable = true)
    private User user;

    @Column(nullable = false)
    private String fileName;

    @Column(nullable = false)
    private String url;

    @Column(nullable = false)
    private boolean isConfirmed = false;

    /// 생성자
    @Builder
    protected Image(User user, String fileName, String url) {
        this.user = user;
        this.fileName = fileName;
        this.url = url;
        this.isConfirmed = false;
    }

    /// 정적 팩토리 메서드
    public static Image of(User user, String fileName, String url) {
        return Image.builder()
                .user(user)
                .fileName(fileName)
                .url(url)
                .build();
    }

    /// 임시 생성 정적 팩토리 메서드
    public static Image temporaryOf(String fileName, String url) {
        return Image.builder()
                .user(null)
                .fileName(fileName)
                .url(url)
                .build();
    }


    /// 비즈니스 로직
    // 사용한다고 확정하는 메서드
    public void confirm(User user) {
        this.user = user;
        this.isConfirmed = true;
    }

    // 사용한다고 확정하는 메서드
    public void confirm() {

        /// 예외처리
        if (this.user == null) {
            throw new IllegalStateException("사용자 정보가 없는 이미지는 확정할 수 없습니다.");
        }

        this.isConfirmed = true;
    }


}
