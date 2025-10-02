package bootcamp.kakao.community.common.response.paging;

import lombok.Builder;

@Builder
public record SliceRequest(
        Long lastId,
        int offSet) {
}
