package com.graduation.letter.repository;

import com.graduation.letter.model.letter.Template;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TemplateRepository extends JpaRepository<Template, UUID> {
    Optional<Template> findByIdAndActiveTrue(UUID id);

    List<Template> findAllByActiveTrue(Pageable pageable);
}
