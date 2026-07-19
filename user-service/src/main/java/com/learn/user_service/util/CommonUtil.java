package com.learn.user_service.util;

import com.learn.user_service.model.AuthUserDetails;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface CommonUtil {

    static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
    static final Logger log = LogManager.getLogger(CommonUtil.class);

    static boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        }
        Matcher matcher = EMAIL_PATTERN.matcher(email);
        return matcher.matches();
    }

    static AuthUserDetails extractUserDetailsFromToken(String auth, ObjectMapper objectMapper) {
        log.info("Extracting user details from token");
        if (auth == null || !auth.startsWith("Bearer ")) {
            log.error("Token is invalid");
            throw new RuntimeException("Token is invalid");
        }
        // Implementation for extracting claims from token
        try {
            String[] parts = auth.split("\\.");
            String encodedDetails = parts[1];

            byte[] decodedBytes = Base64.getDecoder().decode(encodedDetails);
            String decodedJson = new String(decodedBytes, StandardCharsets.UTF_8);

            AuthUserDetails authUserDetails = objectMapper.readValue(decodedJson, AuthUserDetails.class);
            verifyExtractedUser(authUserDetails);

            return authUserDetails;
        } catch (JacksonException e) {
            log.error("Failed to parse user details from token");
            throw new RuntimeException("Failed to parse user details from token", e);
        }
    }

    private static void verifyExtractedUser(AuthUserDetails userDetails) {
        if (userDetails == null) {
            log.error("User details could not be extracted from token.");
            throw new RuntimeException("User details could not be extracted from token.");
        }
    }


}
