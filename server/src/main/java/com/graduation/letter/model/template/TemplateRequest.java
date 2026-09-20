package com.graduation.letter.model.template;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record TemplateRequest(

        @NotBlank(message = "Title is required")
        String title,

        @NotBlank(message = "Template is required")
        String template,

        String type,

        List<String> placeholders
) {
}
