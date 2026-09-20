package com.graduation.letter.model.guest;

import com.graduation.letter.common.GuestType;
import com.graduation.letter.model.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "guests")
@Getter
@Setter
@NoArgsConstructor
public class Guest extends BaseEntity {

    @Id
    @GeneratedValue(generator = "uuid-v7-generator")
    private UUID id;

    private String name;

    private String phoneNumber;

    private GuestType type;

    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, String> additionalInfo;

    public Guest(GuestRequest request) {
        this.name = request.name();
        this.phoneNumber = request.phoneNumber();
        this.type = GuestType.fromString(request.type());
        this.additionalInfo = request.additionalInfo();
    }

    public void update(GuestRequest request) {
        this.name = request.name();
        this.phoneNumber = request.phoneNumber();
        this.type = GuestType.fromString(request.type());
        this.additionalInfo = request.additionalInfo();
    }
}
