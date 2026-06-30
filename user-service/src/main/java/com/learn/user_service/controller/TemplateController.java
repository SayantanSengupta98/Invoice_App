package com.learn.user_service.controller;

import com.learn.user_service.dto.UserResDto;
import com.learn.user_service.service.TemplateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user/template")
@RequiredArgsConstructor
@Slf4j
public class TemplateController {

    private final TemplateService templateService;

    @GetMapping
    public UserResDto getTemplate(@RequestHeader String authorization) {
        log.debug("Fetching template for user: {}", authorization);
        return new UserResDto("Template fetched successfully", "success");
    }


    @PostMapping
    public ResponseEntity<?> createTemplate(@RequestHeader String authorization)
    {
        log.debug("Saving template for user: {}", authorization);
        return ResponseEntity.ok("Template saved successfully for user: ");
    }

}
