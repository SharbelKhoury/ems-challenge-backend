package tech.getarrays.employeemanager.resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class FileStorageService {

    @Value("${upload.path}")
    private String uploadPath;

    public String saveFile(MultipartFile file) {
        try {
            // Log the upload path for debugging
            System.out.println("Upload path: " + uploadPath);

            // Create the uploads directory if it does not exist
            File directory = new File(uploadPath);
            if (!directory.exists()) {
                boolean created = directory.mkdirs(); // Create the directory
                if (!created) {
                    throw new RuntimeException("Could not create upload directory: " + uploadPath);
                }
            }

            // Check if the file is empty
            if (file.isEmpty()) {
                throw new RuntimeException("Cannot save empty file");
            }

            // Create a file object with the original filename
            File serverFile = new File(directory, file.getOriginalFilename());
            System.out.println("Saving file to: " + serverFile.getAbsolutePath()); // Log the file path
            file.transferTo(serverFile); // Save the file to the server
            return serverFile.getAbsolutePath(); // Return the file path
        } catch (IOException e) {
            e.printStackTrace(); // Log the stack trace
            throw new RuntimeException("Failed to save file: " + e.getMessage());
        }
    }
}