package com.graduation.letter.mapper;

import com.graduation.letter.model.letter.Template;
import com.graduation.letter.model.letter.TemplateRequest;
import com.graduation.letter.model.letter.TemplateResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TemplateMapper {

    Template toEntity(TemplateRequest request);

    TemplateResponse toResponse(Template letter);
}