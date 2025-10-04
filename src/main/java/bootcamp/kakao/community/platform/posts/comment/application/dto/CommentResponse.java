package bootcamp.kakao.community.platform.posts.comment.application.dto;

import bootcamp.kakao.community.platform.posts.comment.domain.entity.Comment;
import bootcamp.kakao.community.platform.user.application.dto.UserResponse;
import lombok.Builder;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import java.util.List;

@Builder
public record CommentResponse(

        UserResponse user,
        Long parentId,
        String content,
        boolean editable
) {

    /// 정적 팩토리 메서드
    public static CommentResponse from(Comment comment) {
        return CommentResponse.builder()
                .user(UserResponse.from(comment.getUser()))
                .parentId(comment.getParent() != null ? comment.getParent().getId() : null)
                .content(comment.getContent())
                .build();
    }

    public static Slice<CommentResponse> from(Slice<Comment> posts) {

        /// 값 생성
        List<CommentResponse> commentResponses = posts.stream()
                .map(CommentResponse::from)
                .toList();

        /// Slice 객체 생성
        return new SliceImpl<>(commentResponses, posts.getPageable(), posts.hasNext());
    }

}
