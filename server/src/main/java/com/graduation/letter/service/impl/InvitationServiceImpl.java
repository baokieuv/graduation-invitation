package com.graduation.letter.service.impl;

import com.graduation.letter.common.GuestType;
import com.graduation.letter.exception.ApiException;
import com.graduation.letter.exception.ErrorCode;
import com.graduation.letter.model.guest.GuestResponse;
import com.graduation.letter.model.invitation.InvitationResponse;
import com.graduation.letter.model.template.TemplateResponse;
import com.graduation.letter.service.interfaces.ConfigService;
import com.graduation.letter.service.interfaces.GuestService;
import com.graduation.letter.service.interfaces.InvitationService;
import com.graduation.letter.service.interfaces.TemplateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
@RequiredArgsConstructor
public class InvitationServiceImpl implements InvitationService {

    private final TemplateService templateService;
    private final GuestService guestService;
    private final ConfigService configService;
    private final Random random = new Random();

    // Compile regex pattern once for performance to extract {keys}
    private static final Pattern PLACEHOLDER_PATTERN = Pattern.compile("\\{(.+?)\\}");

    @Override
    public InvitationResponse getInvitation(String identifier) {
        // 1. Retrieve the guest's data
        GuestResponse guest = guestService.getGuestByPhoneNumber(identifier, false); // Vẫn dùng hàm cũ nếu service chưa đổi tên tham số

        // 2. Determine Guest Type (Fallback to ANONYMOUS if guest is null)
        GuestType guestType = (guest != null && guest.type() != null)
                ? GuestType.fromString(guest.type())
                : GuestType.ANONYMOUS;

        // 3. Fetch templates
        List<TemplateResponse> templates = templateService.getAllTemplates(1L, 100L, guestType);

        // 4. Fallback to ANONYMOUS templates if current type templates are empty
        if (templates.isEmpty() && guestType != GuestType.ANONYMOUS) {
            log.warn("No templates found for type {}. Falling back to ANONYMOUS templates.", guestType);
            templates = templateService.getAllTemplates(1L, 100L, GuestType.ANONYMOUS);
        }

        if (templates.isEmpty()) {
            log.error("No active templates found in the database, even for anonymous.");
            throw new ApiException(ErrorCode.RESOURCE_NOT_FOUND, "No invitation templates available.");
        }

        // 5. Select a random template
        TemplateResponse selectedTemplate = templates.get(random.nextInt(templates.size()));

        // 6. Determine fallback name if guest is null
        String fallbackName = "friend";
        if (guest == null && identifier != null && !identifier.trim().isEmpty()) {
            String trimmedId = identifier.trim();
            // Nếu identifier chứa chữ cái (không phải là số điện thoại / ID)
            boolean isNumericIdentifier = trimmedId.matches("^[\\+\\-\\s\\(\\)\\.0-9]+$");
            if (!isNumericIdentifier) {
                // Định dạng lại tên (xoá dấu cách thừa)
                fallbackName = trimmedId.replaceAll("\\s+", " ");
            }
        }

        // 7. Process both the title and the content through the placeholder replacer
        String processedTitle = replacePlaceholders(selectedTemplate.title(), guest, fallbackName);
        String processedContent = replacePlaceholders(selectedTemplate.template(), guest, fallbackName);

        // 8. Return the finalized invitation
        return new InvitationResponse(processedTitle, processedContent);
    }

    /**
     * Replaces placeholders using a priority system: Guest attributes -> Guest Metadata -> System Configs.
     */
    private String replacePlaceholders(String text, GuestResponse guest, String fallbackName) {
        if (text == null || text.isBlank()) {
            return text;
        }

        Matcher matcher = PLACEHOLDER_PATTERN.matcher(text);
        StringBuilder result = new StringBuilder();

        while (matcher.find()) {
            String key = matcher.group(1).trim();
            String replacement = resolvePlaceholder(key, guest, fallbackName);

            // If a replacement is found, substitute it (escaping special regex characters like $)
            // If not found, leave the original placeholder intact for debugging
            if (replacement != null && !replacement.isEmpty()) {
                matcher.appendReplacement(result, Matcher.quoteReplacement(replacement));
            } else {
                matcher.appendReplacement(result, Matcher.quoteReplacement(matcher.group(0)));
            }
        }
        matcher.appendTail(result);

        return result.toString();
    }

    /**
     * Resolves the value for a specific placeholder key based on hierarchy.
     */
    private String resolvePlaceholder(String key, GuestResponse guest, String fallbackName) {
        // 1. Check Guest Base Fields
        if (guest != null) {
            if ("name".equalsIgnoreCase(key) && guest.name() != null) {
                return guest.name();
            }

            // 2. Check Guest Additional Info (Metadata) overrides
            if (guest.additionalInfo() != null && guest.additionalInfo().containsKey(key)) {
                return guest.additionalInfo().get(key);
            }
        } else {
            // Trường hợp Guest null, gán biến {name} thành fallbackName (tên người dùng nhập vào hoặc "friend")
            if ("name".equalsIgnoreCase(key)) {
                return fallbackName;
            }
        }

        // 3. Fallback to System Configs (e.g., location, time, eventName)
        String configValue = configService.getConfigValue(key);
        if (configValue != null && !configValue.isEmpty()) {
            return configValue;
        }

        return null;
    }
}