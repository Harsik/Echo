// package echo.security;

// import java.util.List;

// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

// import echo.model.Account;
// import lombok.Getter;
// import lombok.Setter;

// @Setter
// @Getter
// public class MyAuthentication extends UsernamePasswordAuthenticationToken{
// 	private static final long serialVersionUID = 1L;
	
// 	Account account;
	
// 	public MyAuthentication(String id, String pw, List authList, Account account) {
// 		super(id, pw, authList);
// 		this.account = account;
// 	}
// }