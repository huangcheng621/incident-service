package com.hsbc.incident.domain.model.entity;

import com.hsbc.incident.infrastructure.generator.IdGenerator;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;

@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Data
@Entity
@Table(name = "incidents")
public class Incident extends AuditableEntity {

    @Id
    @GenericGenerator(name = "id", strategy = IdGenerator.NAME)
    @GeneratedValue(generator = "id")
    private Long id;

    private String name;
    private String description;
    private String status;
    private String severity;
    private String category;
}
