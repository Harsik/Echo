package echo.controller;

import echo.model.AvatarFileInfo;
import echo.model.FileInfo;
import echo.payload.UploadFileResponse;
import echo.service.AccountService;
import echo.service.FileStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/file")
public class FileController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private AccountService accountService;

    @GetMapping("/loadAvatar")
    @PreAuthorize("hasRole('USER')")
    public AvatarFileInfo loadAvatar(@RequestParam(value = "email") String email) {
        return accountService.loadAvatarByEmail(email);
    }

    @PostMapping("/uploadAvatar")
    public UploadFileResponse uploadAvatar(@RequestParam("file") MultipartFile file,
            @RequestParam("email") String email) {
                System.out.println("email : " + email);
        String fileName = fileStorageService.storeFile(file);
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/file/downloadFile/")
                .path(fileName).toUriString();

        accountService.saveAvatar(email, fileName, fileDownloadUri, file.getContentType(), file.getSize());
        return new UploadFileResponse(fileName, fileDownloadUri, file.getContentType(), file.getSize());
    }

    @PostMapping("/loadFiles")
    public List<FileInfo> loadFiles() {
        return fileStorageService.loadFiles();
    }

    @GetMapping("/deleteFile")
    public UploadFileResponse deleteFile(@RequestParam(value = "fileName") String fileName) {
        fileStorageService.deleteFileInfo(fileName);
        return new UploadFileResponse(fileName, "", "", '0');
    }

    @PostMapping("/uploadFile")
    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file) {
        String fileName = fileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/file/downloadFile/")
                .path(fileName).toUriString();
        fileStorageService.saveFileInfo(fileName, fileDownloadUri, file.getContentType(), file.getSize());
        return new UploadFileResponse(fileName, fileDownloadUri, file.getContentType(), file.getSize());
    }

    @PostMapping("/uploadMultipleFiles")
    public List<UploadFileResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
        return Arrays.asList(files).stream().map(file -> uploadFile(file)).collect(Collectors.toList());
    }

    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        // // Load file as Resource
        // System.out.println("fileName: " + fileName);
        // System.out.println("request: " + request);
        Resource resource = fileStorageService.loadFileAsResource(fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if (contentType == null) {
            contentType = "application/octet-stream";
        }
        // System.out.println("contentType: " + contentType);
        // System.out.println("resource.getFilename(): " + resource.getFilename());

        // return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType)).body(resource);
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    // @PostMapping("/downloadMultipleFiles")
    // public ResponseEntity<Resource> downloadMultipleFiles(@RequestBody String[] fileNames,
    // HttpServletRequest request) {
    // for (String fileName : fileNames) {
    // Resource resource = fileStorageService.loadFileAsResource(fileName);
    //         String contentType = null;
    //         try {
    //             contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
    //         } catch (IOException ex) {
    //             logger.info("Could not determine file type.");
    //         }
    //         if (contentType == null) {
    //             contentType = "application/octet-stream";
    //         }
            
    //         System.out.println("resource.getFilename(): " + resource.getFilename());
    //         return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
    //                 .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
    //                 .body(resource); 
    // }
    //     return null;
    // }
    // @PostMapping("/downloadMultipleFiles")
    // public void downloadMultipleFiles(@RequestBody String[] fileNames,
    //         HttpServletRequest request) {
    //     for (String fileName : fileNames) {
    //         downloadFile(fileName, request);
    //     }
    // }
    // @PostMapping("/downloadMultipleFiles")
    // public List<ResponseEntity<Resource>> downloadMultipleFiles(@RequestBody
    // String[] fileNames,
    // HttpServletRequest request) {
    // return Arrays.asList(fileNames).stream().map(fileName ->
    // downloadFile(fileName, request)).collect(Collectors.toList());
    // }
}