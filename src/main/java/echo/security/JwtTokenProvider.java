package echo.security;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import java.util.Date;

// JWT 생성 및 검증을위한 유틸리티 클래스 
// 다음 유틸리티 클래스는 사용자가 성공적으로 로그인 한 후 JWT를 생성하고 
// 요청의 Authorization 헤더에서 보낸 JWT의 유효성을 검사하는 데 사용됩니다.

@Component
public class JwtTokenProvider {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    @Value("${app.jwtSecret}")
    private String jwtSecret; // 암호화 키

    @Value("${app.jwtExpirationInMs}")
    private int jwtExpirationInMs; // 만료일 상수

    public String generateToken(Authentication authentication) { //JWT 생성

        AccountPrincipal accountPrincipal = (AccountPrincipal) authentication.getPrincipal();

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);
        return Jwts.builder()
                .setSubject(Long.toString(accountPrincipal.getId())) //PAYLOAD에 들어갈 sub
                .setIssuedAt(new Date()) // 생성일
                .setExpiration(expiryDate) // 만료일
                .signWith(SignatureAlgorithm.HS512, jwtSecret) // 암호화 방식
                .compact(); // 토큰 생성 메소드
    }

    public Long getUserIdFromJWT(String token) { // JWT로 부터 UserId 획득
        Claims claims = 
        Jwts
            .parser()
            .setSigningKey(jwtSecret)
            .parseClaimsJws(token)
            .getBody();

        return Long.parseLong(claims.getSubject());
    }

    public boolean validateToken(String authToken) { // JWT 유효성 검사
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {
            logger.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            logger.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            logger.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            logger.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            logger.error("JWT claims string is empty.");
        }
        return false;
    }
}