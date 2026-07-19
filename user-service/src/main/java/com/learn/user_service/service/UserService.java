package com.learn.user_service.service;

import com.learn.user_service.entity.User;
import com.learn.user_service.model.AuthUserDetails;
import com.learn.user_service.repository.UserRepository;
import com.learn.user_service.util.CommonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    public User saveUserShopName(String authHeader, String shopName) {
        AuthUserDetails authUserDetails = CommonUtil.extractUserDetailsFromToken(authHeader, objectMapper);
        Optional<User> user = userRepository.findByUserEmail(authUserDetails.userEmail());

        return saveUserIfNotPresent(shopName, user, authUserDetails);
    }

    public User saveUserIfNotPresent(String shopName, Optional<User> user, AuthUserDetails authUserDetails) {
        User savedUser = null;
        if (user.isPresent()) {
            savedUser = user.get();
            log.info("User already exists with email: {}", authUserDetails.userEmail());
            if (shopName != null && !shopName.equals(user.get().getShopName())) {
                log.info("Updating user's shop name {}", shopName);
                savedUser.setShopName(shopName);
                savedUser = userRepository.save(savedUser);
            }
        }
        else {
            log.info("Creating the user with email: {}", authUserDetails.userEmail());
            User newUser = new User();
            newUser.setUserId(authUserDetails.userId());
            newUser.setUserName(authUserDetails.userName());
            newUser.setUserEmail(authUserDetails.userEmail());
            newUser.setShopName(shopName);
            savedUser = userRepository.save(newUser);
        }
        return savedUser;
    }

    public User findUser(String authHeader) {
        AuthUserDetails authUserDetails = CommonUtil.extractUserDetailsFromToken(authHeader, objectMapper);
        Optional<User> user = userRepository.findByUserEmail(authUserDetails.userEmail());

        return user.orElse(null);
    }
}
