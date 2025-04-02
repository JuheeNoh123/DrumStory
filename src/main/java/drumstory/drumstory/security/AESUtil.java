package drumstory.drumstory.security;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Component
public class AESUtil {
    private static final String ALGORITHM = "AES";
    @Value("${AES.secret}")
    private String secret; // ✅ static 제거 (Spring이 값을 주입할 수 있도록)

    private static byte[] SECRET_KEY; // ✅ static 변수 선언 (초기화는 나중에)

    @PostConstruct // ✅ Spring이 빈을 초기화한 후 실행됨
    public void init() {
        SECRET_KEY = Base64.getDecoder().decode(secret);
         // ✅ 이제 secret 값이 정상적으로 들어감!
    }

    // 암호화
    public static String encrypt(String data) throws Exception {
        SecretKey key = new SecretKeySpec(SECRET_KEY, ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedData = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encryptedData);
    }

    // 복호화
    public static String decrypt(String encryptedData) throws Exception {
        SecretKey key = new SecretKeySpec(SECRET_KEY, ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decodedData = Base64.getDecoder().decode(encryptedData);
        return new String(cipher.doFinal(decodedData));
    }
}