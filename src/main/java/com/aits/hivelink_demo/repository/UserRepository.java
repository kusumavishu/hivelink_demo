package com.aits.hivelink_demo.repository;

import com.aits.hivelink_demo.entity.UserEntity;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {
//    Optional<UserEntity> fin
//    boolean existsByEmail(String email);
}
