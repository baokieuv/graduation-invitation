package com.graduation.letter.service.impl;

import com.graduation.letter.model.config.Config;
import com.graduation.letter.repository.ConfigRepository;
import com.graduation.letter.service.interfaces.ConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConfigServiceImpl implements ConfigService {

    private final ConfigRepository configRepository;

    @Override
    @Cacheable(value = "config", key = "#key")
    public String getConfigValue(String key) {
        Config config = configRepository.findByKey(key).orElse(null);
        if (config != null) {
            return config.getValue();
        }
        return "";
    }

    @Override
    public String getConfigValue(String key, String defaultValue) {
        Config config = configRepository.findByKey(key).orElse(null);
        if (config != null) {
            return config.getValue();
        }
        return defaultValue;
    }

    @Override
    public Map<String, String> getConfigValue(List<String> keys) {
        Map<String, String> configMap = new HashMap<>();

        for (String key : keys) {
            configRepository.findByKey(key).ifPresent(config -> configMap.put(key, config.getValue()));
        }
        return configMap;
    }

    @Override
    public Config updateConfigValue(String key, String value) {
        Config config = configRepository.findByKey(key).orElse(null);

        if (config == null) {
            config = new Config();
            config.setKey(key);
        }
        config.setValue(value);
        return configRepository.save(config);
    }
}
