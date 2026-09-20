package com.graduation.letter.service.interfaces;

import com.graduation.letter.model.config.Config;

import java.util.List;
import java.util.Map;

public interface ConfigService {

    String getConfigValue(String key);

    String getConfigValue(String key, String defaultValue);

    Map<String, String> getConfigValue(List<String> keys);

    Config updateConfigValue(String key, String value);
}
