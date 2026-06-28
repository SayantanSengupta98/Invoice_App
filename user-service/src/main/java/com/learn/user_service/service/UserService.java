package com.learn.user_service.service;

import com.learn.user_service.dto.UserReqDto;
import com.learn.user_service.dto.UserResDto;
import com.learn.user_service.entity.User;
import com.learn.user_service.repository.UserRepository;
import com.learn.user_service.util.CommonUtil;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserResDto register(UserReqDto userReqDto) {

        if (!CommonUtil.isValidEmail(userReqDto.userEmail()))
            return new UserResDto("Invalid Email. Enter correct email.", "Failed");


        if(userRepository.existsByUserEmail(userReqDto.userEmail()))
            return new UserResDto("User with this email already exists.", "Failed");

        User user = mapUser(userReqDto);

        String userId = userRepository.save(user).getUserId();

        return new UserResDto("User is registered with ID: " + userId, "Success");

    }

    private @NonNull User mapUser(UserReqDto userReqDto) {
        User user = new User();
        user.setUsername(userReqDto.userName());
        user.setPassword(passwordEncoder.encode(userReqDto.password()));
        user.setUserEmail(userReqDto.userEmail());
        return user;
    }


}
