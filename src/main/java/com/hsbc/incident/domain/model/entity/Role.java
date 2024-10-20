package com.hsbc.incident.domain.model.entity;


import com.hsbc.incident.domain.model.value.Permission;
import com.hsbc.incident.infrastructure.generator.IdGenerator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;

@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Data
@Entity
@Table(name = "roles")
public class Role extends AuditableEntity {

    @Id
    @GenericGenerator(name = "id", strategy = IdGenerator.NAME)
    @GeneratedValue(generator = "id")
    private Long id;

    private String name;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany
    @JoinColumn(name = "role_id")
    public Set<RolePermission> rolePermissions = new HashSet<>();

    public List<Permission> getPermissions() {
        return rolePermissions.stream()
            .map(RolePermission::getPermission)
            .collect(Collectors.toList());
    }
}
