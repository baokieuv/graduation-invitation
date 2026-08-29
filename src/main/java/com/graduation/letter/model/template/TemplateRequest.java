package com.graduation.letter.model.template;

import jakarta.validation.constraints.NotBlank;

public record TemplateRequest(

        @NotBlank(message = "Title is required")
        String title,

        @NotBlank(message = "Template is required")
        String template
) {
}
