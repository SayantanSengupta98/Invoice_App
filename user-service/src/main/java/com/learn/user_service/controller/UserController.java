package com.learn.user_service.controller;

import com.learn.user_service.dto.UserResDto;
import com.learn.user_service.service.UserService;
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
        log.debug("Saving Shop name for user" );
        UserResDto userResDto = userService.saveUserShopName(authorization,shopName.trim());
        return ResponseEntity.ok().body(userResDto);
    }

}
