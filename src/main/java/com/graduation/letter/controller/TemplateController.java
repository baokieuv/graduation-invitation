package com.graduation.letter.controller;

import com.graduation.letter.model.letter.TemplateRequest;
import com.graduation.letter.model.letter.TemplateResponse;
import com.graduation.letter.service.TemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/v1/templates")
@RequiredArgsConstructor
public class TemplateController {

    private final TemplateService templateService;

    @PostMapping
    public ResponseEntity<TemplateResponse> createTemplate(
            @RequestBody @Valid TemplateRequest request
    ) {
        TemplateResponse createdLetter = templateService.createTemplate(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdLetter);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TemplateResponse> getTemplateById(@PathVariable("id") String id) {
        TemplateResponse letter = templateService.getTemplateById(id);
        return ResponseEntity.ok(letter);
    }

    @GetMapping
    public ResponseEntity<List<TemplateResponse>> getAllTemplates(
            @RequestParam(required = false, defaultValue = "1") Long page,
            @RequestParam(required = false, defaultValue = "10") Long size
    ) {
        List<TemplateResponse> letters = templateService.getAllTemplates(page, size);
        return ResponseEntity.ok(letters);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TemplateResponse> updateTemplate(
            @PathVariable("id") String id,
            @RequestBody @Valid TemplateRequest request
    ) {
        TemplateResponse updatedLetter = templateService.updateTemplate(id, request);
        return ResponseEntity.ok(updatedLetter);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTemplate(@PathVariable("id") String id) {
        templateService.deleteTemplate(id);
        return ResponseEntity.ok("Template deleted successfully");
    }
}