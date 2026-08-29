package com.graduation.letter.model.guest;

import com.graduation.letter.model.BaseEntity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.UUID;

public class Guest extends BaseEntity {

    @Id
    @GeneratedValue(generator = "uuid-v7-generator")
    private UUID id;

    private String name;

    private String phoneNumber;
}
