package com.sparos.uniquone.msapostservice.util.jwt;

import com.sparos.uniquone.msapostservice.util.response.ExceptionCode;
import com.sparos.uniquone.msapostservice.util.response.UniquOneServiceException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Slf4j
@Component
public class JwtProvider {

    private static String tokenNameOfRequestHeader;

    private static String key;

    private static Long expiredTimeMs;

    private static Long re_expiredTimeMs;

    @Value("${token.name}")
    public void setTokenNameOfRequestHeader(String tokenNameOfRequestHeader) {
        this.tokenNameOfRequestHeader = tokenNameOfRequestHeader;
    }

    @Value("${token.secret}")
    public void setKey(String key) {
        this.key = key;
    }

    @Value("${token.expiration_time}")
    public void setExpiredTimeMs(Long expiredTimeMs) {
        this.expiredTimeMs = expiredTimeMs;
    }

    @Value("${refreshToken.expiration_time}")
    public void setRe_expiredTimeMs(Long re_expiredTimeMs) {
        this.re_expiredTimeMs = re_expiredTimeMs;
    }

    //claims에 넣었던거 받아오기.
    private static Claims extractClaims(String token) {
        try {
            return Jwts.parserBuilder().setSigningKey(getKey(key)).build().parseClaimsJws(token)
                    .getBody();
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            throw new UniquOneServiceException(ExceptionCode.INVALID_TOKEN, HttpStatus.OK);
        } catch (ExpiredJwtException e) {
            throw new UniquOneServiceException(ExceptionCode.Expired_TOKEN, HttpStatus.OK);
        } catch (UnsupportedJwtException e) {
            throw new UniquOneServiceException(ExceptionCode.UNSUPPORTED_TOKEN, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            throw new UniquOneServiceException(ExceptionCode.EMPTY_PAYLOAD_TOKEN, HttpStatus.OK);
        }
    }

    //토큰검증 얘로 쓸거임.
    public static boolean validateToken(String barerToken) {
        try {
            String token = barerToken.replace("Bearer ", "");
            Jwts.parserBuilder().setSigningKey(getKey(key)).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            throw new UniquOneServiceException(ExceptionCode.INVALID_TOKEN, HttpStatus.OK);
        } catch (ExpiredJwtException e) {
            throw new UniquOneServiceException(ExceptionCode.Expired_TOKEN, HttpStatus.OK);
        } catch (UnsupportedJwtException e) {
            throw new UniquOneServiceException(ExceptionCode.UNSUPPORTED_TOKEN, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            throw new UniquOneServiceException(ExceptionCode.EMPTY_PAYLOAD_TOKEN, HttpStatus.OK);
        }
    }

    public static boolean isJwtValid(HttpServletRequest request) {
        String token = getTokenFromRequestHeader(request);

        //유효시간 테스트
        checkValidTokenTime(token);

        boolean returnValue = true;

        String subject = null;

        try {
            subject = extractClaims(token).getSubject();

        } catch (Exception ex) {
            returnValue = false;
        }

        if (subject == null || subject.isEmpty()) {
            returnValue = false;
        }

        return returnValue;
    }

    //토큰 유효 시간 검증.
    public static boolean checkValidTokenTime(String token) throws RuntimeException {
        try {
            Date expiredDate = extractClaims(token).getExpiration();
            return expiredDate.before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    //데이터 타입 확인해보기.
    public static Long getUserPkId(String token) {
        return extractClaims(token).get("id", Long.class);
    }

    public static Long getUserPkId(HttpServletRequest request) {
        String token = getTokenFromRequestHeader(request);
        return getUserPkId(token);
    }

    public static String getUserNickName(String token) {
        return extractClaims(token).get("nickName", String.class);
    }

    public static String getUserNickName(HttpServletRequest request) {
        String token = request.getHeader(tokenNameOfRequestHeader);
        return getUserNickName(token);
    }

    public static String getUserEmail(String token) {
        return extractClaims(token).get("email", String.class);
    }

    public static String getUserEmail(HttpServletRequest request) {
        String token = request.getHeader(tokenNameOfRequestHeader);
        return getUserEmail(token);
    }

    public static String getUserRole(String token) {
        return extractClaims(token).get("role", String.class);
    }

    public static String getUserRole(HttpServletRequest request) {
        String token = getTokenFromRequestHeader(request);
//        log.info("role = {} ", extractClaims(token).get("role", String.class));
        return extractClaims(token).get("role", String.class);
    }

//    public static Long getUserPkId(HttpServletRequest request){
//        String token = request.getHeader(env.getProperty("token.name"));
//        return (Long) Jwts.parser().setSigningKey(env.getProperty("token.secret")).parseClaimsJws(token).getBody().get("id");
//    }

    public static JwtToken generateToken(Long id, String email, String nickName, String role) {
        //닉네임을 여기서 꺼내쓸일이 있을까.
        Claims claims = Jwts.claims().setSubject(email);
        claims.put("id", id);
        claims.put("nickName", nickName);
        claims.put("email", email);
        claims.put("role", role);

        return new JwtToken(
                Jwts.builder()
                        .setClaims(claims)
                        .setIssuedAt(new Date(System.currentTimeMillis()))
                        .setExpiration(new Date(System.currentTimeMillis() + expiredTimeMs))
                        .signWith(getKey(key), SignatureAlgorithm.HS256)
                        .compact(),
                Jwts.builder()
                        .setClaims(claims)
                        .setIssuedAt(new Date())
                        .setExpiration(new Date(System.currentTimeMillis() + re_expiredTimeMs))
                        .signWith(getKey(key), SignatureAlgorithm.HS256)
                        .compact()
        );
    }

    private static Key getKey(String key) {
        byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public static String getTokenFromRequestHeader(HttpServletRequest request) {
        String bearerToken = request.getHeader(tokenNameOfRequestHeader);
        return bearerToken.replace("Bearer ", "");
    }
}
