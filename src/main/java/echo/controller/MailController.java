package echo.controller;

import echo.payload.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/mail")
public class MailController {

    // @Autowired
    // private MailSender sender;

    @Autowired
    public JavaMailSender emailSender;

    public File convert(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        convFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }
    
    public File multipartToFile(MultipartFile multipart) throws IllegalStateException, IOException {
        File convFile = new File(multipart.getOriginalFilename());
        multipart.transferTo(convFile);
        return convFile;
    }

    @PostMapping("/send")
    // public void sendMail(@RequestParam("file") MultipartFile file, @RequestParam("mail") Mail mail) throws MessagingException, IllegalStateException, IOException {
        public void sendMail(@RequestParam("filePath") String filePath, @RequestParam("mail") Mail mail) throws MessagingException, IllegalStateException, IOException {
        // SimpleMailMessage msg = new SimpleMailMessage();
        // msg.setFrom(mail.getSender());
        // msg.setTo(mail.getReceiver());
        // msg.setSubject(mail.getSubject());
        // msg.setText(mail.getText());
        // this.sender.send(msg);
        
        MimeMessage message = emailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        System.out.println("MimeMessageHelper");
        helper.setFrom(mail.getSender());
        helper.setTo(mail.getReceiver());
        helper.setSubject(mail.getSubject());
        helper.setText(mail.getText());
        System.out.println("MimeMessageHelper set");

        // FileSystemResource fileResource = new FileSystemResource(convert(file));
        FileSystemResource fileResource = new FileSystemResource(new File(filePath));
        System.out.println("FileSystemResource convert");
        helper.addAttachment("Invoice", fileResource);

        emailSender.send(message);
    }

}
