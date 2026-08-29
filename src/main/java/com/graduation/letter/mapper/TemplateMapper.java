package com.graduation.letter.mapper;

import com.graduation.letter.model.template.Template;
import com.graduation.letter.model.template.TemplateRequest;
import com.graduation.letter.model.template.TemplateResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TemplateMapper {

    Template toEntity(TemplateRequest request);

    TemplateResponse toResponse(Template letter);
}