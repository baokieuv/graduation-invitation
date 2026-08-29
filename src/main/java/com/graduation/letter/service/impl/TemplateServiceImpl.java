package com.graduation.letter.service.impl;

import com.graduation.letter.common.Formatter;
import com.graduation.letter.exception.ApiException;
import com.graduation.letter.exception.ErrorCode;
import com.graduation.letter.mapper.TemplateMapper;
import com.graduation.letter.model.template.Template;
import com.graduation.letter.model.template.TemplateRequest;
import com.graduation.letter.model.template.TemplateResponse;
import com.graduation.letter.repository.TemplateRepository;
import com.graduation.letter.service.interfaces.TemplateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
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
        Template newTemplate = mapper.toEntity(template);
        Template savedTemplate = repository.save(newTemplate);
        return mapper.toResponse(savedTemplate);
    }

    @Override
    @Cacheable(value = "templates", key = "#id")
    public TemplateResponse getTemplateById(String id) {
        UUID uuid = Formatter.parseUUID(id);
        Template template = repository.findByIdAndActiveTrue(uuid)
                .orElseThrow(() -> new ApiException(ErrorCode.RESOURCE_NOT_FOUND, "Template not found with id: " + id));

        return mapper.toResponse(template);
    }

    @Override
    public List<TemplateResponse> getAllTemplates(Long page, Long size) {
        if (page == null || page < 1 || size == null || size < 1) {
            throw new IllegalArgumentException("page and size must be >= 1");
        }

        Pageable pageable = Pageable.ofSize(Math.toIntExact(size)).withPage(Math.toIntExact(page - 1));
        List<Template> templates = repository.findAllByActiveTrue(pageable);

        return templates.stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    @CachePut(value = "templates", key = "#id")
    public TemplateResponse updateTemplate(String id, TemplateRequest request) {

        Template template = repository.findByIdAndActiveTrue(Formatter.parseUUID(id))
                .orElseThrow(() -> new ApiException(ErrorCode.RESOURCE_NOT_FOUND, "Template not found with id: " + id));

        template.setTitle(request.title());
        template.setTemplate(request.template());

        return mapper.toResponse(repository.save(template));
    }

    @Override
    @CacheEvict(value = "templates", key = "#id")
    public void deleteTemplate(String id) {
        UUID uuid = Formatter.parseUUID(id);
        Template template = repository.findByIdAndActiveTrue(uuid)
                .orElseThrow(() -> new ApiException(ErrorCode.RESOURCE_NOT_FOUND, "Template not found with id: " + id));

        template.setActive(false);
        repository.save(template);
    }
}
