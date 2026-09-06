package com.aits.hivelink_demo.mapper;

import com.aits.hivelink_demo.dto.request.UserRequest;
import com.aits.hivelink_demo.dto.response.FileUploadResponse;
import com.aits.hivelink_demo.dto.response.UserResponse;
import com.aits.hivelink_demo.entity.UserEntity;
import com.aits.hivelink_demo.enums.KycStatus;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserEntity toEntity(
            UserRequest request,
            FileUploadResponse profileImageUpload,
            FileUploadResponse aadhaarDocumentUpload
    ) {

        return UserEntity.builder()
                .profileImageUrl(getFileUrl(profileImageUpload))
                .fullName(request.getFullName())
                .mobileNumber(request.getMobileNumber())
                .email(request.getEmail())
                .emergencyContact(request.getEmergencyContact())
                .gender(request.getGender())
                .city(request.getCity())
                .state(request.getState())
                .aadhaarNumber(request.getAadhaarNumber())
                .aadhaarDocumentUrl(getFileUrl(aadhaarDocumentUpload))
                .aadhaarStatus(KycStatus.PENDING)
                .bloodGroup(request.getBloodGroup())
                .occupation(request.getOccupation())
                .referralCode(request.getReferralCode())
                .selfieUrl(null)
                .build();
    }

    public UserResponse toResponse(UserEntity entity) {

        return UserResponse.builder()
                .userId(entity.getUserId())
                .profileImageUrl(entity.getProfileImageUrl())
                .fullName(entity.getFullName())
                .mobileNumber(entity.getMobileNumber())
                .email(entity.getEmail())
                .emergencyContact(entity.getEmergencyContact())
                .gender(entity.getGender())
                .city(entity.getCity())
                .state(entity.getState())
                .aadhaarNumber(entity.getAadhaarNumber())
                .aadhaarDocumentUrl(entity.getAadhaarDocumentUrl())
                .aadhaarStatus(
                        entity.getAadhaarStatus() != null
                                ? entity.getAadhaarStatus().name()
                                : null
                )
                .bloodGroup(entity.getBloodGroup())
                .occupation(entity.getOccupation())
                .referralCode(entity.getReferralCode())
                .selfieUrl(entity.getSelfieUrl())
                .build();
    }

    private String getFileUrl(FileUploadResponse upload) {
        return upload != null ? upload.getFileUrl() : null;
    }
}
