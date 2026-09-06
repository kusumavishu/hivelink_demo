package com.aits.hivelink_demo.dto.request;

import com.aits.hivelink_demo.enums.BloodGroup;
import com.aits.hivelink_demo.enums.Gender;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    private MultipartFile profileImage;

    @NotBlank(message = "Full name is required")
    @Size(
            min = 2,
            max = 100,
            message = "Full name must be between 2 and 100 characters"
    )
    @Pattern(
            regexp = "^[a-zA-Z]+(?:[\\s'-][a-zA-Z]+)*$",
            message = "Full name can contain only letters, spaces, apostrophes, and hyphens"
    )
    private String fullName;

    @NotBlank(message = "Mobile number is required")
    @Pattern(
            regexp = "^\\+?[1-9]\\d{7,14}$",
            message = "Mobile number must be valid"
    )
    private String mobileNumber;

    @NotBlank(message = "Email address is required")
    @Email(message = "Please enter a valid email address")
    @Size(max = 254, message = "Email address must not exceed 254 characters")
    private String email;

    @NotBlank(message = "Mobile number is required")
    @Pattern(
            regexp = "^\\+?[1-9]\\d{7,14}$",
            message = "Mobile number must be valid"
    )
    private String emergencyContact;

    @NotNull(message = "Gender is required")
    private Gender gender;

    @Size(max = 100, message = "City must not exceed 100 characters")
    private String city;

    @Size(max = 100, message = "State must not exceed 100 characters")
    private String state;

    @Pattern(
            regexp = "^[2-9][0-9]{11}$",
            message = "Aadhaar number must be a valid 12-digit number"
    )
    private String aadhaarNumber;

    private MultipartFile aadhaarDocument;

    @NotNull(message = "Blood Group is required")
    private BloodGroup bloodGroup;

    @Size(max = 100, message = "Occupation must not exceed 100 characters")
    private String occupation;

    @Size(max = 100, message = "Referral Code must not exceed 100 characters")
    private String referralCode;
}
