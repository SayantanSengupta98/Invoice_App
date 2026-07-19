package com.learn.user_service.controller;

import com.learn.user_service.dto.UserResDto;
import com.learn.user_service.entity.User;
import com.learn.user_service.service.UserService;
import com.learn.user_service.util.Constants;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user/")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @PostMapping("/shopName")
    public ResponseEntity<?> shopName(@RequestHeader String authorization, @RequestBody @NonNull String shopName) {
        log.info("Saving Shop name for user");
        User user = userService.saveUserShopName(authorization, shopName.trim());
        return ResponseEntity.ok()
                .body(new UserResDto<String>("Shop name saved successfully, for userName " + user.getUserName(),
                "Current shop name is: " + user.getShopName(),
                Constants.SUCCESS));
    }

    @GetMapping
    public ResponseEntity<?> findUser(@RequestHeader String authorization)
    {
        log.info("Fetching user info");
        User user = userService.findUser(authorization);
        if (user == null) {
            return ResponseEntity.ok().body(new UserResDto<>("User not found", "No user found for the provided auth Token", Constants.FAILED));
        }
        return ResponseEntity.ok().body(new UserResDto<>("User found", user, Constants.SUCCESS));
    }
}
