package com.graduation.letter.model.template;

import com.graduation.letter.model.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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
}
