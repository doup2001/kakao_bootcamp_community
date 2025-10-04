package bootcamp.kakao.community.common.response.paging;

import lombok.Builder;
import org.springframework.data.domain.Slice;

import java.util.List;

@Builder
public record SliceResponse<T>(
        List<T> content,
        boolean hasNext
) {
    public static <T> SliceResponse<T> from(Slice<T> slice) {
        return SliceResponse.<T>builder()
                .content(slice.getContent())
                .hasNext(slice.hasNext())
                .build();
    }
}
