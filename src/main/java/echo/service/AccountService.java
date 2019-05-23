package echo.service;

import java.time.LocalDateTime;
import java.util.Collections;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import echo.model.Account;
import echo.model.FileInfo;
import echo.model.Profile;
import echo.payload.ProfilePayload;
import echo.payload.SignRequest;
import echo.repository.AccountRepository;
import echo.repository.FileInfoRepository;
import echo.repository.ProfileRepository;
import echo.security.AccountPrincipal;

@Service
public class AccountService implements UserDetailsService {

        @Autowired
        private AccountRepository accountRepository;

        @Autowired
        private ProfileRepository profileRepository;

        @Autowired
        private FileInfoRepository fileInfoRepository;

        @Override
        @Transactional
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                Account account = accountRepository.findByEmail(username).orElseThrow(
                                () -> new UsernameNotFoundException("User not found with username : " + username));
                return AccountPrincipal.create(account);
                // 여기서 UserDetails에 대한 상세 프로퍼티들을 정의하고 리턴 할 수 있으나 jpa 혹은 redis 등에서 serializable
                // 문제가 발생한다
                // 이 문제를 해결 할려고 entity class 혹은 model folder에 있는 java 파일들에 implement
                // serializable 하였으나 해결 되지 않는다.
                // DAO 형성 과정에서 interface인 core.UserDetails가 이미 extends serializable이기 때문에 문제가
                // 발생한다.
                // 고로 UserDetails 에서 새로운 class를 받아 return하는 것으로 이 문제를 해결 할 수 있다.
        }

        @Transactional
        public UserDetails loadUserById(Long id) {
                Account account = accountRepository.findById(id)
                                .orElseThrow(() -> new UsernameNotFoundException("User not found with id : " + id));

                return AccountPrincipal.create(account);
        }

        public void saveAvatar(String email, String name, String downloadUri, String type, Long size) {
                Account account = accountRepository.findByEmail(email).orElseThrow(
                                () -> new UsernameNotFoundException("Account not found with email : " + email));

                Profile profile = profileRepository.findByAccountId(account.getId())
                                .orElseThrow(() -> new UsernameNotFoundException(
                                                "Profile not found with account_id : " + account.getId()));

                // FileInfo fileInfo = fileInfoRepository.save(new FileInfo(name, downloadUri,
                // type, size));
                // profile.setFileInfos(Collections.singleton(fileInfo));
                // if(profile.getFileInfos() != null) {
                // profile.getFileInfos().clear();
                // }

                // FileInfo fileInfo = new FileInfo(name, downloadUri, type, size);
                // profile.setFileInfos(Collections.singleton(fileInfo));

                profileRepository.save(profile);
        }

        public Account createProfile(ProfilePayload profilePayload) {
                String name = profilePayload.getName();
                String bio = profilePayload.getBio();
                String company = profilePayload.getCompany();
                String address = profilePayload.getAddress();

                Account account = accountRepository.findByEmail(profilePayload.getEmail())
                                .orElseThrow(() -> new UsernameNotFoundException(
                                                "Account not found with email : " + profilePayload.getEmail()));

                Profile profile = new Profile(name, bio, company, address);

                account.setProfile(profile);
                profile.setAccount(account);

                return accountRepository.save(account);
        }

        public Account editProfile(ProfilePayload profilePayload) {
                String name = profilePayload.getName();
                String bio = profilePayload.getBio();
                String company = profilePayload.getCompany();
                String address = profilePayload.getAddress();

                Account account = accountRepository.findByEmail(profilePayload.getEmail())
                                .orElseThrow(() -> new UsernameNotFoundException(
                                                "Account not found with email : " + profilePayload.getEmail()));
                // Profile profile =
                // profileRepository.findByAccountId(account.getId()).orElseThrow(
                // () -> new UsernameNotFoundException("Profile not found with id : "
                // +account.getId()));

                Profile profile = new Profile(name, bio, company, address);

                account.setProfile(profile);
                profile.setAccount(account);

                return accountRepository.save(account);
        }

        public Profile loadProfile(String email) {
                Account account = accountRepository.findByEmail(email).orElseThrow(
                                () -> new UsernameNotFoundException("Account not found with email : " + email));
                Profile profile = account.getProfile();

                return profile;
        }

}