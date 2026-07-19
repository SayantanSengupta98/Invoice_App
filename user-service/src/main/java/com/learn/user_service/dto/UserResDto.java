package com.learn.user_service.dto;

public record UserResDto<T> (String message,
                          T content,
                          String status) {
}
