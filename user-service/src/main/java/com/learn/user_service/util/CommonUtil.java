package com.learn.user_service.util;

import com.learn.user_service.dto.UserReqDto;
import com.learn.user_service.entity.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface CommonUtil {

    static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    static boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        }
        Matcher matcher = EMAIL_PATTERN.matcher(email);
        return matcher.matches();
    }

    static Predicate<User> isAuthenticated(UserReqDto u, BCryptPasswordEncoder passwordEncoder) {
        return user -> user != null && passwordEncoder.matches(u.password(), user.getPassword());
    }

}
