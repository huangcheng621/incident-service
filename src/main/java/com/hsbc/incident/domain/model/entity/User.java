package com.hsbc.incident.domain.model.entity;

import com.hsbc.incident.domain.model.value.Permission;
import com.hsbc.incident.infrastructure.generator.IdGenerator;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
@Table(name = "users")
public class User extends AuditableEntity {

    @Id
    @GenericGenerator(name = "id", strategy = IdGenerator.NAME)
    @GeneratedValue(generator = "id")
    private Long id;

    private String username;

    // The best way to store password is to use a hash value.
    // However, for simplicity, we store the password as plain text.
    private String password;

    private String firstName;
    private String lastName;
    private String email;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "user_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public Set<Permission> getPermissions() {
        return roles.stream()
            .flatMap(role -> role.getPermissions().stream())
            .collect(Collectors.toSet());
    }
}
