package com.aits.hivelink_demo.service.impl;

import com.aits.hivelink_demo.dto.request.UserRequest;
import com.aits.hivelink_demo.dto.response.FileUploadResponse;
import com.aits.hivelink_demo.dto.response.UserResponse;
import com.aits.hivelink_demo.entity.UserEntity;
import com.aits.hivelink_demo.mapper.UserMapper;
import com.aits.hivelink_demo.repository.UserRepository;
import com.aits.hivelink_demo.service.FileStorageService;
import com.aits.hivelink_demo.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;
    private final FileStorageService fileStorageService;
    private final UserMapper userMapper;

    public UserServiceImpl(
            UserRepository userRepository,
            FileStorageService fileStorageService,
            UserMapper userMapper
    ) {
        this.userRepository = userRepository;
        this.fileStorageService = fileStorageService;
        this.userMapper = userMapper;
    }

    @Override
    public UserResponse registerUser(UserRequest request) {
        FileUploadResponse profileImageUpload = null;
        FileUploadResponse aadhaarDocumentUpload = null;

        // Upload profile image
        if (request.getProfileImage() != null &&
                !request.getProfileImage().isEmpty()) {

            profileImageUpload =fileStorageService.upload(
                    request.getProfileImage(),
                    "profiles"
            );
        }

        // Upload Aadhaar document
        if (request.getAadhaarDocument() != null &&
                !request.getAadhaarDocument().isEmpty()) {

            aadhaarDocumentUpload = fileStorageService.upload(
                    request.getAadhaarDocument(),
                    "document"
            );
        }

        // Request → Entity
        UserEntity userEntity = userMapper.toEntity(
                request,
                profileImageUpload,
                aadhaarDocumentUpload
        );

        // Save
        UserEntity savedUser = userRepository.save(userEntity);

        // Entity → Response
        return userMapper.toResponse(savedUser);
    }
}
