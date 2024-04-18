package org.highfives.esc.user.controller;

import lombok.extern.slf4j.Slf4j;
import org.highfives.esc.user.entity.MemberImage;
import org.highfives.esc.user.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Slf4j
@RequestMapping("/file")
@RestController
public class FileUploadController {

    private final FileService fileService;
    @Autowired
    public FileUploadController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/upload")
    public MemberImage uploadImage(@RequestParam("file") MultipartFile file, @RequestParam("userId") int userId) throws IOException {


        return fileService.uploadImage(file, userId);
    }

    @GetMapping("/{user_id}")
    public ResponseEntity<Resource> getImage(@PathVariable int userId) {
        Resource resource = fileService.getImageResource(userId);
        return ResponseEntity.ok()
                .body(resource);
    }
}
