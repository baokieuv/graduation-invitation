package com.graduation.letter.model.letter;

public record TemplateResponse(
        String id,
        String template,
        String createdAt,
        String updatedAt
) {
}
