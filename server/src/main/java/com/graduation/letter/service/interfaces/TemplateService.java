package com.graduation.letter.service.interfaces;

import com.graduation.letter.model.template.TemplateRequest;
import com.graduation.letter.model.template.TemplateResponse;

import java.util.List;

public interface TemplateService {

    TemplateResponse createTemplate(TemplateRequest template);

    TemplateResponse getTemplateById(String id);

    List<TemplateResponse> getAllTemplates(Long page, Long size);

    TemplateResponse updateTemplate(String id, TemplateRequest template);

    void deleteTemplate(String id);
}
