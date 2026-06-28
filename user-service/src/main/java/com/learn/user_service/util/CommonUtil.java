package com.learn.user_service.util;

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

}
