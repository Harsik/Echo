package echo.controller;

import echo.exception.AppException;
import echo.model.Account;
import echo.model.Role;
import echo.model.RoleName;
import echo.payload.ApiResponse;
import echo.payload.JwtAuthenticationResponse;
import echo.payload.SignRequest;
import echo.repository.AccountRepository;
import echo.repository.RoleRepository;
import echo.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;

import java.net.URI;
import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody SignRequest signRequest) {
        // ResponseEntity 타입은 Http 의 상태코드를 함께 전송해주기 위해 사용한다.
        // ResponseEntity 오브젝트는 HTTP 응답의 바디에 삽입할 오브젝트를 소유한다.(예제에서 Customer 해당)
        // ResponseEntity 오브젝트는 바디 오브젝트에 추가해 스테이터스 코드와 HTTP 응답 헤더를 설정할 수 있다.
        // @RequestBody 혹은 @ResponseBody는 자바객체를 http으로(json) http를 자바객체로 변환해준다.
        // 고로 @RequestBody를 사용할려면 변환하고자 하는 자바객체 즉 class를 생성하여야한다.
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signRequest.getEmail(), signRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignRequest signRequest) {
        if (accountRepository.existsByEmail(signRequest.getEmail())) {
            return new ResponseEntity(new ApiResponse(false, "Username is already taken!"), HttpStatus.BAD_REQUEST);
        }
        // Creating user's account
        Account accont = new Account(signRequest.getEmail(), signRequest.getPassword());

        accont.setPassword(passwordEncoder.encode(accont.getPassword()));

        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new AppException("Account Role not set."));
        
        accont.setRoles(Collections.singleton(userRole));

        Account result = accountRepository.save(accont);

        URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/accounts/{email}")
                .buildAndExpand(result.getEmail()).toUri();
        return ResponseEntity.created(location).body(new ApiResponse(true, "Account registered successfully"));
        // fromCurrentContextPath로 생성된 uri를 넣어주면
        // HTTP Header에 Location=http://localhost/api/users/{username}에 정보가 들어간다.
        // Response header에 Location:http://localhost:8080/api/users/user2@email.com 확인
        // 전 프로젝트에서 유저 프로파일을 불러 오기 위한 선언으로 보인다. 나는 현재 쓸모 없으니 변경
        // 이제 나도 프로파일 작업을 시작하였다 고로 다시 원복시켰다.
        // return ResponseEntity.ok(new ApiResponse(true, "Account registered successfully"));
    }

    // @PostMapping("/logout")
    // public ResponseEntity<?> deAuthenticateUser() {
    //     Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    //     if (auth != null) {
    //         SecurityContextHolder.clearContext();
    //     }
    //     return ResponseEntity.ok("Authentication Successfully Clear");
    // }

}