package echo.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// 이 클래스는 적절한 인증없이 보호 된 자원에 액세스하려고 시도하는 클라이언트에게 401 권한이 부여되지 않은 오류를 반환하는 데 사용됩니다.
// 이거 안하면 console에 http status 401 뜨고 끝임
// 그것은 Spring Security의 AuthenticationEntryPoint인터페이스를 구현 합니다.
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationEntryPoint.class);

    @Override //commence()를 override하여 401에러 응답을 구현한다.
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
            AuthenticationException e) throws IOException, ServletException {
        logger.error("Responding with unauthorized error. Message - {}", e.getMessage());
        httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
    }
}