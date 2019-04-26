package echo.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import echo.model.Account;
import echo.service.AccountService;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class AuthProvider implements AuthenticationProvider{
	@Autowired
    AccountService accountService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String userId = authentication.getName();
		String userPw = authentication.getCredentials().toString();
		return authenticate(userId, userPw);
	}
	
	private Authentication authenticate(String id, String pw) throws AuthenticationException {
		Account account = new Account(id, pw);
		account = accountService.getByUserName(id);
		if ( account == null || !account.getPassword().equals(pw)) {
			log.error("{} is not exist or password is not equals", id);
			return null;
		}
		List authList = new ArrayList<>();
        /** 
		 * Role 처리 필요, 일단 임의로 USER Role을 부여한다.  
		**/
		authList.add(new SimpleGrantedAuthority("ROLE_USER"));		
        return new MyAuthentication(id, pw, authList, account);
	}

	@Override
	public boolean supports(Class authentication) {
		return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
	}
}