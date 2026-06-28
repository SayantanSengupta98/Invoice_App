package com.learn.user_service.controller;

import com.learn.user_service.dto.UserReqDto;
import com.learn.user_service.dto.UserResDto;
import com.learn.user_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public UserResDto register(@RequestBody UserReqDto u)
    {
        return userService.register(u);
    }

}
