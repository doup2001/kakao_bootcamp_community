package bootcamp.kakao.community.platform.posts.post.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;

/**
 * Embeddable로 구현한 후, 레디스의 값으로서 추가구현해서 사용하는 걸로
 */
@Embeddable
@Getter
public class PostStat {

    @Column(nullable = false)
    private int viewCount;

    @Column(nullable = false)
    private int commentCount;

    @Column(nullable = false)
    private int likeCount;

    /// 기본 값으로 생성자
    protected PostStat() {
        this.viewCount = 0;
        this.commentCount = 0;
        this.likeCount = 0;
    }

}
