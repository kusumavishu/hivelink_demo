package com.aits.hivelink_demo.entity;

import com.aits.hivelink_demo.enums.Gender;
import com.aits.hivelink_demo.enums.BloodGroup;
import com.aits.hivelink_demo.enums.KycStatus;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID userId;


    @Column
    private String profileImageUrl;

    @Column(nullable = false, length = 50)
    private String fullName;

    @Column(nullable = false, unique = true, length = 16)
    private String mobileNumber;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column
    private String emergencyContact;

    @Column
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column
    private String city;

    @Column
    private String state;

    @Column(unique = true)
    private String aadhaarNumber;

    @Column
    private String aadhaarDocumentUrl;

    @Column
    @Enumerated(EnumType.STRING)
    private BloodGroup bloodGroup;

    @Column
    private String occupation;

    @Column
    private String referralCode;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private KycStatus aadhaarStatus = KycStatus.NOT_SUBMITTED;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private KycStatus selfieStatus = KycStatus.NOT_SUBMITTED;

    @Column
    private String selfieUrl;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private KycStatus kycStatus = KycStatus.NOT_SUBMITTED;

    @Builder.Default
    @Column(nullable = false)
    private Boolean userActive = true;
}
