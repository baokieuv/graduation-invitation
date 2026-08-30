package com.graduation.letter.model.template;

import com.graduation.letter.model.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "letters")
@Getter
@Setter
public class Template extends BaseEntity {
    @Id
    @GeneratedValue(generator = "uuid-v7-generator")
    private UUID id;

    private String title;

    private String template;

    @JdbcTypeCode(SqlTypes.JSON)
    private List<String> placeholders;
}
