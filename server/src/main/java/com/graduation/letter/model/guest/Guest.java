package com.graduation.letter.model.guest;

import com.graduation.letter.model.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "guests")
@Getter
@Setter
public class Guest extends BaseEntity {

    @Id
    @GeneratedValue(generator = "uuid-v7-generator")
    private UUID id;

    private String name;

    private String phoneNumber;

    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, String> additionalInfo;
}
