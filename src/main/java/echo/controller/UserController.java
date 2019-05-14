package echo.controller;

import echo.payload.*;
import echo.repository.AccountRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private AccountRepository accountRepository;

    @GetMapping("/user/checkEmailAvailability")
    public AccountIdentityAvailability checkEmailAvailability(@RequestParam(value = "email") String email) {
        Boolean isAvailable = !accountRepository.existsByEmail(email);
        return new AccountIdentityAvailability(isAvailable);
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
