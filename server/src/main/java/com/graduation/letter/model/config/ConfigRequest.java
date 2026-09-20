package com.graduation.letter.model.config;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ConfigRequest(

        @NotBlank(message = "Key cannot be blank")
        String key,

        @NotNull(message = "Value cannot be null")
        String value
) {
}
