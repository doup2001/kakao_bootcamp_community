package bootcamp.kakao.community.common.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;


/**
 * 응답을 하는 커스텀 예외 클래스입니다.
 * ErrorCode와 필드별 에러 정보를 함께 담아, 일관된 에러 응답을 제공합니다.
 */

@Getter
@RequiredArgsConstructor
public class CustomException extends RuntimeException{

    private final ErrorCode errorCode;

    private final List<FieldErrorResponse> fieldErrorResponses;


}
