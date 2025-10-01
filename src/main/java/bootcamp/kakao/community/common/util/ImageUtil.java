package bootcamp.kakao.community.common.util;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ImageUtil {

    /// 파일 이름을 고유하게 생성하는 메서드
    public String generateKey(String file) {

        /// 랜덤 UUID
        String key = UUID.randomUUID().toString();

        /// 확장자 처리
        String extension = file.substring(file.lastIndexOf(".") + 1);

        return key + "." + extension;
    }

}
