package com.learn.user_service.service;

import com.learn.user_service.entity.Template;
import com.learn.user_service.entity.User;
import com.learn.user_service.model.AuthUserDetails;
import com.learn.user_service.repository.TemplateRepository;
import com.learn.user_service.util.CommonUtil;
import com.learn.user_service.util.Constants;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TemplateService {

    private final ChatClient chatClient;
    private final ObjectMapper objectMapper;
    private final UserService userService;
    private final TemplateRepository templateRepository;


    public String generateTemplate(String authHeader) {

        log.debug("Generating template with AI for user started" );

        String htmlContent = chatClient.prompt(Constants.TEMPLATE_PROMPT).call().content();

        log.debug("AI Generated HTML content: {}", htmlContent);

        return htmlContent;
    }

    public Long saveUserWithTemplate(String authHeader, @NonNull String htmlContent) {
        // Implementation for saving user with template
        Template template = new Template();
        template.setTemplateHtmlContent(htmlContent);

        User savedUser = userService.saveUserShopName(authHeader, null);

        template.setTemplateName(savedUser.getUserEmail().concat("_").concat(String.valueOf(System.currentTimeMillis())));

        if (savedUser.getCurrentTemplate() == null) {
            savedUser.setCurrentTemplate(template);
        }
        template.setUser(savedUser);

        Template savedTemplate = templateRepository.save(template);

        return savedTemplate.getTemplateId();
    }

    public List<Template> findAllTemplatesByUser(String authHeader)
    {
        AuthUserDetails authUserDetails = CommonUtil.extractUserDetailsFromToken(authHeader, objectMapper);
        // Implementation for finding all templates by user
        return templateRepository.findByUserId(authUserDetails.userId());
    }
}
