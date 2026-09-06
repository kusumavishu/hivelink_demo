package com.aits.hivelink_demo.service;

import com.aits.hivelink_demo.dto.response.FileUploadResponse;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {

    FileUploadResponse upload(
            MultipartFile file,
            String folder
    );

    void delete(String filePath);

    String getPublicUrl(String file);
}
