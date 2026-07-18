package com.learn.user_service.service;

import com.learn.user_service.dto.UserResDto;
import com.learn.user_service.entity.User;
import com.learn.user_service.model.AuthUserDetails;
import com.learn.user_service.repository.UserRepository;
import com.learn.user_service.util.CommonUtil;
import com.learn.user_service.util.Constants;
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

    public UserResDto saveUserShopName(String authHeader, String shopName) {
        AuthUserDetails authUserDetails = CommonUtil.extractUserDetailsFromToken(authHeader, objectMapper);
        Optional<User> user = userRepository.findByUserEmail(authUserDetails.userEmail());


        if (user.isPresent()) {
            log.info("User already exists with email: {}", authUserDetails.userEmail());
            if (!shopName.equals(user.get().getShopName())) {
                log.info("Updating user's shop name {}", shopName);
                User savedUser = user.get();
                savedUser.setShopName(shopName);
                userRepository.save(savedUser);
            }

        }
        else {
            log.info("Creating the user with email: {}", authUserDetails.userEmail());
            User newUser = new User();
            newUser.setUserId(authUserDetails.userId());
            newUser.setUserName(authUserDetails.userName());
            newUser.setUserEmail(authUserDetails.userEmail());
            newUser.setShopName(shopName);
            userRepository.save(newUser);
        }


        return new UserResDto("User shop name saved successfully, for userName " + authUserDetails.userName() + ".", Constants.SUCCESS);
    }
}
