package bootcamp.kakao.community.platform.report.domain.entity;

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
@Table(name = "report")
public class Report extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReportType type;

    @Column(nullable = false)
    private Long contentId;

    /// 생성자
    @Builder
    protected Report(User user, ReportType type, Long contentId) {
        this.user = user;
        this.contentId = contentId;
        this.type = type;
    }

    /// 정적 팩토리 메서드
    public static Report of(User user, ReportType type, Long contentId) {
        return Report.builder()
                .user(user)
                .type(type)
                .contentId(contentId)
                .build();
    }

}
