package com.aits.hivelink_demo.service;

import com.aits.hivelink_demo.dto.request.UserRequest;
import com.aits.hivelink_demo.dto.response.UserResponse;
import com.aits.hivelink_demo.entity.UserEntity;

public interface UserService {
    UserResponse registerUser(UserRequest request);
}
