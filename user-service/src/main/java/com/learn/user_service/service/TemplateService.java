package com.learn.user_service.service;

import com.learn.user_service.entity.Template;
import com.learn.user_service.entity.User;
import com.learn.user_service.model.AuthUserDetails;
import com.learn.user_service.repository.TemplateRepository;
import com.learn.user_service.repository.UserRepository;
import com.learn.user_service.util.CommonUtil;
import com.learn.user_service.util.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TemplateService {

    private final ChatClient chatClient;
    private final ObjectMapper objectMapper;
    private final UserRepository userRepository;
    private final TemplateRepository templateRepository;


    public String generateTemplate(String authHeader) {

        //saveUserwithTemplate(authHeader);

        String htmlContent = chatClient.prompt(Constants.TEMPLATE_PROMPT).call().content();

        //String htmlContent = "abc";

        Long templateId = saveUserWithTemplate(authHeader, htmlContent);
        return templateId.toString();
    }


    private Long saveUserWithTemplate(String authHeader, String htmlContent) {

        // Implementation for saving user with template
        AuthUserDetails authUserDetails = CommonUtil.extractUserDetailsFromToken(authHeader, objectMapper);
        Template template = new Template();
        template.setTemplateHtmlContent(htmlContent);
        template.setTemplateName(authUserDetails.userEmail().concat("_").concat(String.valueOf(System.currentTimeMillis())));


        verifyExtractedUser(authUserDetails);

        Optional<User> user = userRepository.findByUserEmail(authUserDetails.userEmail());


        if (user.isPresent()) {
            template.setUser(user.get());

        }
        else {

            User newUser = new User();
            newUser.setUserId(authUserDetails.userId());
            newUser.setUserName(authUserDetails.userName());
            newUser.setUserEmail(authUserDetails.userEmail());
            newUser.setCurrentTemplate(template);

            template.setUser(newUser);
        }

        Template savedTemplate = templateRepository.save(template);

        return savedTemplate.getTemplateId();
    }

    private static void verifyExtractedUser(AuthUserDetails userDetails) {
        if (userDetails == null) {
            log.error("User details could not be extracted from token.");
            throw new RuntimeException("User details could not be extracted from token.");
        }
    }

}
