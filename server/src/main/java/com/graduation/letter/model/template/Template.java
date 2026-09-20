package com.graduation.letter.model.template;

import com.graduation.letter.common.GuestType;
import com.graduation.letter.model.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "templates")
@Getter
@Setter
@NoArgsConstructor
public class Template extends BaseEntity {
    @Id
    @GeneratedValue(generator = "uuid-v7-generator")
    private UUID id;

    @Column(columnDefinition = "TEXT")
    private String title;

    @Column(columnDefinition = "TEXT")
    private String template;

    @JdbcTypeCode(SqlTypes.JSON)
    private List<String> placeholders;

    private GuestType type;

    public Template(TemplateRequest request) {
        this.title = request.title();
        this.template = request.template();
        this.placeholders = request.placeholders();
        this.type = GuestType.fromString(request.type());
    }
}
