package com.aits.hivelink_demo.dto.response;

import com.aits.hivelink_demo.enums.BloodGroup;
import com.aits.hivelink_demo.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private UUID userId;
    private String profileImageUrl;
    private String fullName;
    private String mobileNumber;
    private String email;
    private String emergencyContact;
    private Gender gender;
    private String city;
    private String state;
    private String aadhaarNumber;
    private String aadhaarDocumentUrl;
    private String aadhaarStatus;
    private BloodGroup bloodGroup;
    private String occupation;
    private String referralCode;
    private String selfieUrl;
}