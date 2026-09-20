package com.graduation.letter.common;

import lombok.Getter;

@Getter
public enum GuestType {
    FRIEND(1),
    WORKMATE(2),
    SPECIAL(3),
    ANONYMOUS(4);

    private final Integer value;

    GuestType(Integer value) {
        this.value = value;
    }

    public static GuestType fromValue(Integer value) {
        for (GuestType type : GuestType.values()) {
            if (type.getValue().equals(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid GuestType value: " + value);
    }

    public static GuestType fromString(String type) {
        for (GuestType guestType : GuestType.values()) {
            if (guestType.name().equalsIgnoreCase(type)) {
                return guestType;
            }
        }
        throw new IllegalArgumentException("Invalid GuestType: " + type);
    }
}
