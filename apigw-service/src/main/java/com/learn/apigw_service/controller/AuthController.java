package com.learn.apigw_service.controller;

import com.learn.apigw_service.dto.UserReqDto;
import com.learn.apigw_service.dto.UserResDto;

import com.learn.apigw_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth/user")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public UserResDto register(@RequestBody UserReqDto u) {
        return userService.register(u);
    }

    @PostMapping("/signIn")
    public ResponseEntity<?> login(@RequestBody UserReqDto u) {
        return userService.login(u);
    }

//    @GetMapping()
//    public ResponseEntity<?> fetchUser(@RequestBody String userEmail) {
//        return ResponseEntity.ok().body(userService.getByUserEmail(userEmail));
//    }

}
