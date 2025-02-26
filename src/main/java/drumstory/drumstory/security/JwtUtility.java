package drumstory.drumstory.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import java.security.Key;
import java.util.Date;

@Service
public class JwtUtility {

    private final Key secret;
    private static final long expirationTime = 1000 * 60 * 60; // 1시간
    public JwtUtility() {
        this.secret = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    }

    // JWT 생성
    public String generateToken(String memberId) {
        return "Bearer " + Jwts.builder()
                .setSubject(memberId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(secret)
                .compact();
    }

    // JWT 클레임 반환
    public String getMemberNum(String token) {
        // 토큰 파싱 및 클레임 반환
        return Jwts.parser()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject(); // 유효한 경우, 클레임 반환
    }

    // JWT 유효성 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secret)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            System.out.println("Token expired");
        } catch (SignatureException e) {
            System.out.println("Invalid signature");
        } catch (MalformedJwtException e) {
            System.out.println("Invalid token");
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
