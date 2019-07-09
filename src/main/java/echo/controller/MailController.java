package echo.controller;

import echo.payload.*;
import echo.service.FileStorageService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/mail")
public class MailController {

    // @Autowired
    // private MailSender sender;
    @Autowired
    private FileStorageService fileStorageService;

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
    public void sendMail(@RequestParam("file") MultipartFile file, @RequestParam("sender") String sender,
            @RequestParam("receiver") String receiver, @RequestParam("subject") String subject,
            @RequestParam("text") String text) throws MessagingException, IOException {

        MimeMessage message = emailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        System.out.println("MimeMessageHelper");
        helper.setFrom(sender);
        helper.setTo(receiver);
        helper.setSubject(subject);
        helper.setText(text);
        String fileName = fileStorageService.storeFile(file);
        FileSystemResource fileResource = new FileSystemResource(convert(file));
        helper.addAttachment(fileName, fileResource);

        emailSender.send(message);
    }

}
