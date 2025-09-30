package bootcamp.kakao.community.platform.posts.category.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @Column(nullable = false)
    private String name;

    /// 빌더 생성자
    @Builder
    protected Category(Category parent, String name) {
        this.parent = parent;
        this.name = name;
    }

    /// 정적 팩토리 메서드
    public static Category of(Category parent, String name) {
        return Category.builder()
                .parent(parent)
                .name(name)
                .build();
    }

    /// 비즈니스 로직

}
