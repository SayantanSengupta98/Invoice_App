package com.learn.user_service.controller;

import com.learn.user_service.dto.SaveTemplateRequestDto;
import com.learn.user_service.dto.UserResDto;
import com.learn.user_service.entity.Template;
import com.learn.user_service.service.TemplateService;
import com.learn.user_service.util.Constants;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/template")
@RequiredArgsConstructor
@Slf4j
public class TemplateController {

    private final TemplateService templateService;

    @GetMapping
    public ResponseEntity<?> getAllTemplate(@RequestHeader String authorization) {
        log.debug("Fetching all templates for user");
        List<Template> templates = templateService.findAllTemplatesByUser(authorization);
        if (templates != null && !templates.isEmpty()) {
            return ResponseEntity.ok().body(new UserResDto<>("Template fetched successfully",
                    templates,
                    Constants.SUCCESS));
        }
        else {
            return ResponseEntity.ok().body(new UserResDto<>("No templates found for user",
                    templates,
                    Constants.SUCCESS));
        }
    }


    @GetMapping("/new")
    public ResponseEntity<?> createTemplate(@RequestHeader String authorization) {
        log.debug("Creating template for user: {}", authorization);
        String templateHtml = templateService.generateTemplate(authorization);
        return ResponseEntity.ok().body(new UserResDto<>("Template fetched successfully",
                templateHtml,
                Constants.SUCCESS));
    }

    @PostMapping
    public ResponseEntity<?> saveTemplate(@RequestHeader String authorization,
                                          @RequestBody @NonNull SaveTemplateRequestDto requestDto) {
        log.debug("Saving template for user: {}", authorization);
        if (requestDto.htmlTemplate() == null || requestDto.htmlTemplate().isEmpty()) {
            return ResponseEntity.badRequest().body(new UserResDto<>("HTML template content cannot be empty", "Bad Request", Constants.FAILED));
        }
        Long templateId = templateService.saveUserWithTemplate(authorization, requestDto.htmlTemplate());
        return ResponseEntity.ok().body(new UserResDto<>("Template saved successfully", templateId, Constants.SUCCESS));
    }
}
