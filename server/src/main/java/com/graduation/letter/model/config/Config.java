package com.graduation.letter.model.config;

import com.graduation.letter.model.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "configs")
@Getter
@Setter
public class Config extends BaseEntity {

    @Id
    @GeneratedValue(generator = "uuid-v7-generator")
    private UUID id;

    @Column(name = "config_key")
    private String key;

    private String value;
}
