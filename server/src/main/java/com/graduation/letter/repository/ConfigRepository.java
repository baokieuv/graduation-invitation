package com.graduation.letter.repository;

import com.graduation.letter.model.config.Config;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ConfigRepository extends JpaRepository<Config, UUID> {
    Optional<Config> findByKey(String key);
}
