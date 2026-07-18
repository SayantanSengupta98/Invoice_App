package com.learn.user_service.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AuthUserDetails(
        @JsonProperty("sub")
        String userId,
        String userName,
        String userEmail) {
}