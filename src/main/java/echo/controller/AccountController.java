package echo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import echo.model.Account;
import echo.service.AccountService;

@RestController
public class AccountController {

    @Autowired
    AccountService accountService;

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

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Account account, String error, String logout) {
        if (error != null) {
           // account.addAttribute("errorMsg", "Your username and password are invalid.");
        }
        if (logout != null) {
          //  account.addAttribute("msg", "You have been logged out successfully");
        }
        return "login.html";
    }

}