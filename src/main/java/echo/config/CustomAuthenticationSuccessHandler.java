package echo.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import echo.security.AccountPrincipal;




@Configuration
public class CustomAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws ServletException, IOException {

        // 로그인 성공시 필요한 작업 추가

        System.out.println("[authorities]");
        System.out.println(
                "  " + authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority));

        WebAuthenticationDetails details = (WebAuthenticationDetails) authentication.getDetails();
        System.out.println("[details]");
        System.out.println("  IP Address : " + details.getRemoteAddress());
        System.out.println("  Session ID : " + details.getSessionId());

        AccountPrincipal principal = (AccountPrincipal) authentication.getPrincipal();
        System.out.println("[principal]");
        System.out.println("  username : " + principal.getUsername());
        System.out.println("  password : " + principal.getPassword());
        System.out.println("  authorities : " + principal.getAuthorities());

        System.out.println("[credentials]");
        System.out.println("  " + authentication.getCredentials());
        super.onAuthenticationSuccess(request, response, authentication);
    }
}