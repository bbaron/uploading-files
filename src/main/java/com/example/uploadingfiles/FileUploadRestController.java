package com.example.uploadingfiles;

import com.example.uploadingfiles.storage.StorageService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static java.util.Objects.requireNonNull;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentContextPath;

@RestController
class FileUploadRestController {
    private final StorageService storageService;

    FileUploadRestController(StorageService storageService) {
        this.storageService = storageService;
    }

    @PostMapping("/uploadFile")
    @CrossOrigin(origins = "http://localhost:8080")
    UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file) {
        storageService.store(file);
        String fileName = requireNonNull(file.getOriginalFilename());
        String fileDownloadUri = fromCurrentContextPath()
                .path("/files/")
                .path(fileName)
                .toUriString();

        return new UploadFileResponse(fileName, fileDownloadUri, file.getContentType(), file.getSize());
    }

    @GetMapping("/test")
    @CrossOrigin(origins = "http://localhost:8080")
    String test() {
        return "ok";
    }
}
