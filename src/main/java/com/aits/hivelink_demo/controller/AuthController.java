package com.aits.hivelink_demo.controller;

import com.aits.hivelink_demo.dto.request.UserRequest;
import com.aits.hivelink_demo.dto.response.UserResponse;
import com.aits.hivelink_demo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(
            UserService userService
    ){
        this.userService = userService;
    }


    @PostMapping(
            value = "/register",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public UserResponse registerAppUser(
            @Valid @ModelAttribute UserRequest request
    ){

        return userService.registerUser(request);
    }

    @GetMapping("/test")
    public String testController(){
        return "DONE SUCCESSFULLY WORKING";
    }
}
