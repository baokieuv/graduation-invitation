package com.graduation.letter.controller;

import com.graduation.letter.common.ApiResponse;
import com.graduation.letter.common.ResponseFactory;
import com.graduation.letter.model.config.Config;
import com.graduation.letter.model.config.ConfigRequest;
import com.graduation.letter.service.interfaces.ConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/config")
@RequiredArgsConstructor
@Slf4j
public class ConfigController {
    private final ConfigService configService;

    private final ResponseFactory responseFactory;

    @GetMapping(params = "keys")
    public ResponseEntity<ApiResponse<Object>> getConfigValue(@RequestParam("keys") List<String> keys) {
        Map<String, String> value = configService.getConfigValue(keys);

        return responseFactory.success(value);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Object>> updateConfigValue(@RequestBody ConfigRequest request) {
        Config config = configService.updateConfigValue(request.key(), request.value());

        return responseFactory.success(config);
    }
}
