package com.graduation.letter.service.impl;

import com.graduation.letter.common.Formatter;
import com.graduation.letter.mapper.TemplateMapper;
import com.graduation.letter.model.letter.Template;
import com.graduation.letter.model.letter.TemplateRequest;
import com.graduation.letter.model.letter.TemplateResponse;
import com.graduation.letter.repository.TemplateRepository;
import com.graduation.letter.service.TemplateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class TemplateServiceImpl implements TemplateService {

    private final TemplateRepository repository;

    private final TemplateMapper mapper;

    @Override
    public TemplateResponse createTemplate(TemplateRequest template) {
        return null;
    }

    @Override
    public TemplateResponse getTemplateById(String id) {
        UUID uuid = Formatter.parseUUID(id);
        Template letter = repository.findByIdAndActiveTrue(uuid)
                .orElseThrow(() -> new RuntimeException("Letter not found with id: " + id));

        return mapper.toResponse(letter);
    }

    @Override
    public List<TemplateResponse> getAllTemplates(Long page, Long size) {

        return List.of();
    }

    @Override
    public TemplateResponse updateTemplate(String id, TemplateRequest template) {
        return null;
    }

    @Override
    public void deleteTemplate(String id) {
        UUID uuid = Formatter.parseUUID(id);
        Template letter = repository.findByIdAndActiveTrue(uuid)
                .orElseThrow(() -> new RuntimeException("Template not found with id: " + id));

        letter.setActive(false);
        repository.save(letter);
    }
}
