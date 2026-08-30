package com.graduation.letter.model.template;

import java.util.List;

public record TemplateResponse(
        String id,

        String title,

        String template,

        List<String> placeholders
) {
}
