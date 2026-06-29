package com.learn.user_service.service;

import com.learn.user_service.dto.TokenResDto;
import com.learn.user_service.dto.UserReqDto;
import com.learn.user_service.dto.UserResDto;
import com.learn.user_service.entity.User;
import com.learn.user_service.repository.UserRepository;
import com.learn.user_service.util.CommonUtil;
import com.learn.user_service.util.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UserResDto register(UserReqDto userReqDto) {

        if (!CommonUtil.isValidEmail(userReqDto.userEmail()))
            return new UserResDto("Invalid Email. Enter correct email.", Constants.FAILED);


        if (userRepository.existsByUserEmail(userReqDto.userEmail()))
            return new UserResDto("User with this email already exists.", Constants.FAILED);

        User user = mapUser(userReqDto);

        String userId = userRepository.save(user).getUserId();

        return new UserResDto("User is registered with ID: " + userId, Constants.SUCCESS);

    }

    private @NonNull User mapUser(UserReqDto userReqDto) {
        User user = new User();
        user.setUsername(userReqDto.userName());
        user.setPassword(passwordEncoder.encode(userReqDto.password()));
        user.setUserEmail(userReqDto.userEmail());
        return user;
    }


    public ResponseEntity<?> login(UserReqDto u) {

        log.debug("auth start");
        User user = userRepository.findByUserEmail(u.userEmail());

        if (!CommonUtil.isAuthenticated(u, passwordEncoder).test(user)) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }

        String authToken = jwtService.generateToken(user);

        return ResponseEntity.status(200).body(new TokenResDto(authToken, Constants.SUCCESS));
    }
}
