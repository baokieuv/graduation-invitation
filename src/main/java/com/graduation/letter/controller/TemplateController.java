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

    private final TemplateService letterService;

    @PostMapping
    public ResponseEntity<TemplateResponse> createLetter(
            @RequestBody @Valid TemplateRequest request
    ) {
        TemplateResponse createdLetter = letterService.createTemplate(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdLetter);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TemplateResponse> getLetterById(@PathVariable("id") String id) {
        TemplateResponse letter = letterService.getTemplateById(id);
        return ResponseEntity.ok(letter);
    }

    @GetMapping
    public ResponseEntity<List<TemplateResponse>> getAllLetters() {
        List<TemplateResponse> letters = letterService.getAllTemplates(10L, 10L);
        return ResponseEntity.ok(letters);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TemplateResponse> updateLetter(
            @PathVariable("id") String id,
            @RequestBody @Valid TemplateRequest request
    ) {
        TemplateResponse updatedLetter = letterService.updateTemplate(id, request);
        return ResponseEntity.ok(updatedLetter);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteLetter(@PathVariable("id") String id) {
        letterService.deleteTemplate(id);
        return ResponseEntity.ok("Template deleted successfully");
    }
}