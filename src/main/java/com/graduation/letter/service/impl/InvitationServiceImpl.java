package com.graduation.letter.service.impl;

import com.graduation.letter.exception.ApiException;
import com.graduation.letter.exception.ErrorCode;
import com.graduation.letter.model.guest.GuestResponse;
import com.graduation.letter.model.invitation.InvitationResponse;
import com.graduation.letter.model.template.TemplateResponse;
import com.graduation.letter.service.interfaces.GuestService;
import com.graduation.letter.service.interfaces.InvitationService;
import com.graduation.letter.service.interfaces.TemplateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
@Slf4j
@RequiredArgsConstructor
public class InvitationServiceImpl implements InvitationService {

    private final TemplateService templateService;
    private final GuestService guestService;
    private final Random random = new Random();

    @Override
    public InvitationResponse getInvitation(String phoneNumber) {
        // 1. Retrieve the guest's data
        GuestResponse guest = guestService.getGuestByPhoneNumber(phoneNumber);

        // 2. Fetch templates. (Assuming < 100 templates exist, fetching page 1 with size 100)
        List<TemplateResponse> templates = templateService.getAllTemplates(1L, 100L);
        if (templates.isEmpty()) {
            log.error("No active templates found in the database.");
            throw new ApiException(ErrorCode.RESOURCE_NOT_FOUND, "No invitation templates available.");
        }

        // 3. Select a random template
        TemplateResponse selectedTemplate = templates.get(random.nextInt(templates.size()));

        // 4. Process both the title and the content through the placeholder replacer
        String processedTitle = replacePlaceholders(selectedTemplate.title(), guest);
        String processedContent = replacePlaceholders(selectedTemplate.template(), guest);

        // 5. Return the finalized invitation
        return new InvitationResponse(processedTitle, processedContent);
    }

    /**
     * Helper method to safely replace all placeholders in a given string.
     */
    private String replacePlaceholders(String text, GuestResponse guest) {
        // If the text is null (e.g., template has no title), just return null
        if (text == null) {
            return null;
        }

        String result = text;

        // Replace standard {{name}} placeholder
        if (guest.name() != null) {
            result = result.replace("{{name}}", guest.name());
        }

        // Replace dynamic placeholders from the guest's additionalInfo map
        if (guest.additionalInfo() != null) {
            for (Map.Entry<String, String> entry : guest.additionalInfo().entrySet()) {
                if (entry.getValue() != null) {
                    String placeholder = "{{" + entry.getKey() + "}}";
                    result = result.replace(placeholder, entry.getValue());
                }
            }
        }

        return result;
    }
}