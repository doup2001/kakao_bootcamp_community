package bootcamp.kakao.community.common.util;

import org.springframework.stereotype.Component;

@Component
public class KeyUtil {

    /// 개별 키 목록
    private static final String SEPARATOR = ":";

    /// JWT
    public static final String REFRESH_TOKEN = "refresh_token";
    public static final String ACCESS_TOKEN = "access_token";
    public static final String ID_CLAIM = "user_id";
    public static final String EMAIL_CLAIM = "email";
    public static final String ROLE_CLAIM = "role";

    /// 합쳐서 사용하는 키 목록

    /// 키 생성 함수
    public static String getRefreshTokenKey(Long userId, String deviceType) {
        return REFRESH_TOKEN + SEPARATOR + userId + SEPARATOR + deviceType;

    }


}
