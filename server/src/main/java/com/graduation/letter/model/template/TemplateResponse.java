package com.graduation.letter.model.template;

import java.util.List;

public record TemplateResponse(
        String id,

        String title,

        String template,

        String type,

        List<String> placeholders
) {

    public TemplateResponse(Template template) {
        this(
                template.getId().toString(),
                template.getTitle(),
                template.getTemplate(),
                template.getType().name(),
                template.getPlaceholders()
        );
    }
}
