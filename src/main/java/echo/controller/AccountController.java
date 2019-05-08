package echo.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import echo.model.Account;
import echo.payload.LoginRequest;
import echo.service.AccountService;

@RestController
// @RequestMapping("/api/auth")
public class AccountController {

    @Autowired
    AuthenticationManager authenticationManager;
    
    @Autowired
    AccountService accountService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        // ResponseEntity 타입은 Http 의 상태코드를 함께 전송해주기 위해 사용한다.
        // ResponseEntity 오브젝트는 HTTP 응답의 바디에 삽입할 오브젝트를 소유한다.(예제에서 Customer 해당)
        // ResponseEntity 오브젝트는 바디 오브젝트에 추가해 스테이터스 코드와 HTTP 응답 헤더를 설정할 수 있다.
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return ResponseEntity.ok(authentication);
    }

    // @GetMapping("/create")
    // public Account create() {
    //     Account account = new Account();
    //     account.setName("user");
    //     account.setPassword("password");
    //     return accountService.save(account);
    // }

    @GetMapping("/iden")
    public String iden() {
        return "your auth is pass";
    }

    // @RequestMapping(value = "/login", method = RequestMethod.GET)
    // public String login(Account account, String error, String logout) {
    //     if (error != null) {
    //        // account.addAttribute("errorMsg", "Your username and password are invalid.");
    //     }
    //     if (logout != null) {
    //       //  account.addAttribute("msg", "You have been logged out successfully");
    //     }
    //     return "login.html";
    // }

}