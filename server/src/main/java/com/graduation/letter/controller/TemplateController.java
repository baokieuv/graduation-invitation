package com.graduation.letter.controller;

import com.graduation.letter.common.ApiResponse;
import com.graduation.letter.common.ResponseFactory;
import com.graduation.letter.common.SuccessCode;
import com.graduation.letter.model.template.TemplateRequest;
import com.graduation.letter.model.template.TemplateResponse;
import com.graduation.letter.service.interfaces.TemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/v1/templates")
@RequiredArgsConstructor
public class TemplateController {

    private final TemplateService templateService;

    private final ResponseFactory responseFactory;

    @PostMapping
    public ResponseEntity<ApiResponse<Object>> createTemplate(
            @RequestBody @Valid TemplateRequest request
    ) {
        TemplateResponse createdLetter = templateService.createTemplate(request);

        return responseFactory.success(createdLetter, SuccessCode.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> getTemplateById(@PathVariable("id") String id) {
        TemplateResponse letter = templateService.getTemplateById(id);

        return responseFactory.success(letter, SuccessCode.OK);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Object>> getAllTemplates(
            @RequestParam(required = false, defaultValue = "1") Long page,
            @RequestParam(required = false, defaultValue = "10") Long size
    ) {
        List<TemplateResponse> letters = templateService.getAllTemplates(page, size);

        return responseFactory.success(letters, SuccessCode.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> updateTemplate(
            @PathVariable("id") String id,
            @RequestBody @Valid TemplateRequest request
    ) {
        TemplateResponse updatedLetter = templateService.updateTemplate(id, request);

        return responseFactory.success(updatedLetter, SuccessCode.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> deleteTemplate(@PathVariable("id") String id) {
        templateService.deleteTemplate(id);

        return responseFactory.success("Template deleted successfully", SuccessCode.OK);
    }
}