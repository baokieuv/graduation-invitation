package com.graduation.letter.service.interfaces;

import com.graduation.letter.common.GuestType;
import com.graduation.letter.model.template.TemplateRequest;
import com.graduation.letter.model.template.TemplateResponse;

import java.util.List;

public interface TemplateService {

    List<TemplateResponse> importTemplates(List<TemplateRequest> templates);

    TemplateResponse createTemplate(TemplateRequest template);

    TemplateResponse getTemplateById(String id);

    List<TemplateResponse> getAllTemplates(Long page, Long size);

    List<TemplateResponse> getAllTemplates(Long page, Long size, GuestType type);

    TemplateResponse updateTemplate(String id, TemplateRequest template);

    void deleteTemplate(String id);
}
