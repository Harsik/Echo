package echo.controller;

import echo.payload.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/mail")
public class MailController {
    
    @Autowired
    private MailSender sender;

    @PostMapping("/send")
    public void sendMail(@RequestBody Mail mail) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(mail.getSender());
        msg.setTo(mail.getReceiver());
        msg.setSubject(mail.getSubject());
        msg.setText(mail.getText());
        this.sender.send(msg);
    }

}
