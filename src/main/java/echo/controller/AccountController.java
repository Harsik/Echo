package echo.controller;

import echo.model.Account;
import echo.model.Profile;
import echo.payload.*;
import echo.repository.AccountRepository;
import echo.service.AccountService;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


@RestController
@RequestMapping("/api/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountRepository accountRepository;
    
    @GetMapping("/checkEmailAvailability")
    public AccountIdentityAvailability checkEmailAvailability(@RequestParam(value = "email") String email) {
        Boolean isAvailable = !accountRepository.existsByEmail(email);
        return new AccountIdentityAvailability(isAvailable);
    }

    @GetMapping("/profile")
    @PreAuthorize("hasRole('USER')")
    public Profile loadProfile(@RequestParam(value = "email") String email) {
        Profile profile = accountService.loadProfile(email);

        return profile;
    }

    @PostMapping("/profile/edit")
    @PreAuthorize("hasRole('USER')")    
    public ResponseEntity<?> editProfile(@RequestBody ProfilePayload profilePayload) {

        Account account = accountService.editProfile(profilePayload);

        //TODO: process POST request
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{accountId}").buildAndExpand(account.getId())
                .toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "Profile edited Successfully"));
    }
    
    // profile not yet 
    // @GetMapping("/users/{username}")
    // public AccountProfile getUserProfile(@PathVariable(value = "username") String username) {
    //     Account account = accountRepository.findByUsername(username)
    //             .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

    //     UserProfile userProfile = new UserProfile(user.getId(), user.getUsername(), user.getName(), user.getCreatedAt());

    //     return userProfile;
    // }
}
