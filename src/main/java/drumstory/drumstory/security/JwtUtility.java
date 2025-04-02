package drumstory.drumstory.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.Date;

@Service
public class JwtUtility {
    @Value("${jwt.secret}") // application.yml 또는 properties에서 주입
    private String secret;
    //private final Key secret;
    private static final long expirationTime = 1000 * 60 * 60; // 1시간
//    public JwtUtility() {
//        this.secret = Keys.secretKeyFor(SignatureAlgorithm.HS512);
//    }

    // JWT 생성
    public String generateToken(String memberId) throws Exception {
        String encryptedSub = AESUtil.encrypt(memberId);
        return "Bearer " + Jwts.builder()
                .setSubject(encryptedSub)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    // JWT 클레임 반환
    public String getMemberNum(String token) throws Exception {
        // 토큰 파싱 및 클레임 반환
        String encryptedSub =  Jwts.parser()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject(); // 유효한 경우, 클레임 반환
        return AESUtil.decrypt(encryptedSub);  // 🔓 복호화
    }

    // JWT 유효성 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secret)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            System.out.println("Token expired: " + e.getMessage());
        } catch (SignatureException e) {
            System.out.println("Invalid signature: " + e.getMessage());
        } catch (MalformedJwtException e) {
            System.out.println("Invalid token: " + e.getMessage());
        }
        return false;
    }

    // 토큰 추출
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);        // "Bearer " 문자 이후의 토큰 부분을 반환
        }
        return null;
    }
}
