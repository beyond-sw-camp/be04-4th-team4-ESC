package org.highfives.esc.user.service;

import org.highfives.esc.user.entity.MemberImage;
import org.highfives.esc.user.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Service
public class FileService {

    private final FileRepository fileRepository;

    @Autowired
    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;

    }

    @Value("${file.upload-dir}")
    private String uploadDir;
//    private final String uploadDir = "C:\\Users\\wj830\\Desktop";

    public MemberImage uploadImage(MultipartFile file, int userId) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            if (fileName.contains("..")) {
                throw new IllegalArgumentException("Invalid file name");
            }

            Path targetLocation = Paths.get(uploadDir + fileName);
            Files.copy(file.getInputStream(), targetLocation);

            MemberImage image = new MemberImage();
            image.setOriginalFileName(fileName);
            image.setRenameFileName(fileName); // You may implement renaming logic here
            image.setSavedUrl(targetLocation.toString());
            image.setUserId(userId);

            return fileRepository.save(image);

        } catch (IOException ex) {
            throw new RuntimeException("Failed to store file: " + fileName, ex);
        }
    }

    public Resource getImageResource(int userId) {
        Optional<MemberImage> optionalImage = fileRepository.findById(userId);
        if (optionalImage.isPresent()) {
            MemberImage image = optionalImage.get();
            String savedUrl = image.getSavedUrl();
            try {
                Path path = Paths.get(savedUrl);
                Resource resource = new UrlResource(path.toUri());
                if (resource.exists()) {
                    return resource;
                } else {
                    throw new RuntimeException("Image not found");
                }
            } catch (IOException ex) {
                throw new RuntimeException("Image not found", ex);
            }
        } else {
            throw new RuntimeException("Image not found");
        }
    }
}
