package echo.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import echo.exception.FileStorageException;
import echo.exception.MyFileNotFoundException;
import echo.model.Account;
import echo.model.Profile;
import echo.model.DBFile;
import echo.payload.UploadFileResponse;
import echo.property.FileStorageProperties;
import echo.repository.AccountRepository;
import echo.repository.ProfileRepository;
import echo.repository.DBFileRepository;

@Service
public class DBFileStorageService {

    // private final Path fileStorageLocation;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ProfileRepository profileRepository;
    
    @Autowired
    private DBFileRepository dbFileRepository;

    public DBFile storeFile(MultipartFile file) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if (fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            DBFile dbFile = new DBFile(fileName, file.getContentType(), file.getBytes());

            return dbFileRepository.save(dbFile);
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public DBFile getFile(String fileId) {
        return dbFileRepository.findById(fileId)
                .orElseThrow(() -> new MyFileNotFoundException("File not found with id " + fileId));
    }
    
    // @Autowired
    // public DBFileStorageService(FileStorageProperties fileStorageProperties) {
    //     this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();

    //     try {
    //         Files.createDirectories(this.fileStorageLocation);
    //     } catch (Exception ex) {
    //         throw new FileStorageException("Could not create the directory where the uploaded files will be stored.",
    //                 ex);
    //     }
    // }

    // public String storeAvatar(UploadFileResponse response, String email) {
    //     Account account = accountRepository.findByEmail(email).orElseThrow(
    //             () -> new UsernameNotFoundException("Account not found with email : " + email));
    //     Profile profile = account.getProfile();
    //     return null;
    // }

    // public String storeFile(MultipartFile file) {
    //     // Normalize file name
    //     String fileName = StringUtils.cleanPath(file.getOriginalFilename());

    //     try {
    //         // Check if the file's name contains invalid characters
    //         if (fileName.contains("..")) {
    //             throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
    //         }

    //         // Copy file to the target location (Replacing existing file with the same name)
    //         Path targetLocation = this.fileStorageLocation.resolve(fileName);
    //         Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

    //         return fileName;
    //     } catch (IOException ex) {
    //         throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
    //     }
    // }

    // public Resource loadFileAsResource(String fileName) {
    //     try {
    //         Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
    //         Resource resource = new UrlResource(filePath.toUri());
    //         if (resource.exists()) {
    //             return resource;
    //         } else {
    //             throw new MyFileNotFoundException("File not found " + fileName);
    //         }
    //     } catch (MalformedURLException ex) {
    //         throw new MyFileNotFoundException("File not found " + fileName, ex);
    //     }
    // }
}