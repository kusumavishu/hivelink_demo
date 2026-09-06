package com.aits.hivelink_demo.service.impl;

import com.aits.hivelink_demo.dto.response.FileUploadResponse;
import com.aits.hivelink_demo.service.FileStorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;
import java.util.UUID;

@Service
public class FileStorageServiceImpl implements FileStorageService {

    private final RestClient restClient;

    private final String supabaseUrl;
    private final String serviceKey;
    private final String bucket;

    public FileStorageServiceImpl(
            @Value("${supabase.url}")
            String supabaseUrl,

            @Value("${supabase.service-key}")
            String serviceKey,

            @Value("${supabase.storage.buckets.images}")
            String bucket
    ) {
        this.supabaseUrl = supabaseUrl;
        this.serviceKey = serviceKey;
        this.bucket = bucket;

        this.restClient = RestClient.builder()
                .baseUrl(supabaseUrl)
                .build();
    }


    @Override
    public FileUploadResponse upload(MultipartFile file, String folder) {

        validateFile(file);

        try{
            String extension = getFileExtension(
                    file.getOriginalFilename()
            );

            String fileName = UUID.randomUUID() + extension;
            String filePath = normalizeFolder(folder) + "/" + fileName;

            String contentType = Objects.requireNonNullElse(
                    file.getContentType(),
                    MediaType.APPLICATION_OCTET_STREAM_VALUE
            );


            restClient
                    .post()
                    .uri(
                            "/storage/v1/object/{bucket}/{filePath}",
                            bucket,
                            filePath
                    )
                    .header(
                            "Authorization",
                            "Bearer " + serviceKey
                    )
                    .header(
                            "apikey",
                            serviceKey
                    )
                    .header(
                            "x-upsert",
                            "false"
                    )
                    .contentType(
                            MediaType.parseMediaType(contentType)
                    )
                    .body(file.getBytes())
                    .retrieve()
                    .onStatus(
                            HttpStatusCode::isError,
                            (request, response) -> {

                                throw new RuntimeException(
                                        "Failed to upload file to Supabase Storage"
                                );
                            }
                    )
                    .toBodilessEntity();

            return FileUploadResponse.builder()
                    .fileName(fileName)
                    .filePath(filePath)
                    .fileUrl(getPublicUrl(filePath))
                    .contentType(contentType)
                    .fileSize(file.getSize())
                    .build();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(String filePath) {
        try{
            restClient
                    .delete()
                    .uri(
                            "/storage/v1/object/{bucket}/{filePath}",
                            bucket,
                            filePath
                    )
                    .header(
                            "Authorization",
                            "Bearer " + serviceKey
                    )
                    .header(
                            "apikey",
                            serviceKey
                    )
                    .retrieve()
                    .onStatus(
                            HttpStatusCode::isError,
                            (request, response) -> {

                                throw new RuntimeException(
                                        "Failed to delete file from Supabase Storage"
                                );
                            }
                    )
                    .toBodilessEntity();
        } catch (Exception e) {
            throw new RuntimeException(e);
        };
    }

    @Override
    public String getPublicUrl(String filePath) {

        return supabaseUrl
                + "/storage/v1/object/public/"
                + bucket
                + "/"
                + filePath;
    }

//

    private void validateFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new RuntimeException(
                    "File is required"
            );
        }
        // 5 MB maximum file size
        long maxFileSize = 5L * 1024 * 1024;

        if (file.getSize() > maxFileSize) {
            throw new RuntimeException(
                    "File size must not exceed 5 MB"
            );
        }
    }


    private String getFileExtension(String fileName){
        if (fileName == null) {
            return "";
        }

        int lastDotIndex = fileName.lastIndexOf(".");
        if (lastDotIndex == -1) {
            return "";
        }

        return fileName.substring(lastDotIndex).toLowerCase();
    }

    private String normalizeFolder(String folder){
        if (folder == null || folder.isBlank()) {
            return "uploads";
        }

        return folder
                .trim()
                .replaceAll("^/+", "")
                .replaceAll("/+$", "");
    }
}
