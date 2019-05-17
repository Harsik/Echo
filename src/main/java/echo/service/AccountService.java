package echo.service;

import java.time.LocalDateTime;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import echo.model.Account;
import echo.payload.ProfilePayload;
import echo.payload.SignRequest;
import echo.repository.AccountRepository;
import echo.security.AccountPrincipal;

@Service
public class AccountService implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;
    
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByEmail(username).orElseThrow(
                () -> new UsernameNotFoundException("User not found with username : " + username));
        return AccountPrincipal.create(account);
        // 여기서 UserDetails에 대한 상세 프로퍼티들을 정의하고 리턴 할 수 있으나 jpa 혹은 redis 등에서 serializable 문제가 발생한다
        // 이 문제를 해결 할려고 entity class 혹은 model folder에 있는 java 파일들에 implement serializable 하였으나 해결 되지 않는다.
        // DAO 형성 과정에서 interface인 core.UserDetails가 이미 extends serializable이기 때문에 문제가 발생한다.
        // 고로 UserDetails 에서 새로운 class를 받아 return하는 것으로 이 문제를 해결 할 수 있다.
    }

    @Transactional
    public UserDetails loadUserById(Long id){
        Account account = accountRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found with id : " + id));

        return AccountPrincipal.create(account);
    }

    public Account editProfile(ProfilePayload profilePayload) {
        Account account = accountRepository.findByEmail(profilePayload.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Account not found with email : " + profilePayload.getEmail()));
        account.setName(profilePayload.getName());
        account.setBio(profilePayload.getBio());
        account.setCompany(profilePayload.getCompany());
        account.setAddress(profilePayload.getAddress());
        account.setUpdateAt(LocalDateTime.now());
        return accountRepository.save(account);
    }
    
    public Account loadProfile(SignRequest signRequest) {
        Account account = accountRepository.findByEmail(signRequest.getEmail()).orElseThrow(
                () -> new UsernameNotFoundException("Account not found with email : " + signRequest.getEmail()));

        return account;
    }

}